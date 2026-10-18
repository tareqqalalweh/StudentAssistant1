
package student.service;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFReader {

    private static final int TOTAL_HOURS = 132;

    private static int lastCompletedHours = 0;

    // =====================================================
    // قراءة PDF
    // =====================================================

    public static String readPDF(File file) {

        try (PDDocument document = Loader.loadPDF(file)) {

            // نستخدم OCR لأن الـ transcript عبارة عن صور
            return readWithOCR(document);

        } catch (Exception e) {

            e.printStackTrace();

            return "Error reading PDF: "
                    + e.getMessage();
        }
    }

    // =====================================================
    // OCR لجميع الصفحات
    // =====================================================

    private static String readWithOCR(
            PDDocument document)
            throws IOException, InterruptedException {

        PDFRenderer renderer =
                new PDFRenderer(document);

        StringBuilder allText =
                new StringBuilder();

        int completedHours = 0;

        for (int page = 0;
                page < document.getNumberOfPages();
                page++) {

            System.out.println(
                    "OCR page " + (page + 1)
            );

            // 200 DPI حتى تكون إحداثيات الـTSV مستقرة
            BufferedImage image =
                    renderer.renderImageWithDPI(
                            page,
                            200
                    );

            Path imagePath =
                    Files.createTempFile(
                            "transcript_page_",
                            ".png"
                    );

            try {

                ImageIO.write(
                        image,
                        "png",
                        imagePath.toFile()
                );

                // =================================================
                // OCR للنص
                // =================================================

                ProcessBuilder textProcess =
                        new ProcessBuilder(
                                "tesseract",
                                imagePath.toString(),
                                "stdout",
                                "-l",
                                "ara+eng",
                                "--psm",
                                "6"
                        );

                textProcess.redirectErrorStream(true);

                Process process =
                        textProcess.start();

                String result =
                        new String(
                                process.getInputStream()
                                        .readAllBytes(),
                                StandardCharsets.UTF_8
                        );

                int exitCode =
                        process.waitFor();

                if (exitCode != 0) {

                    throw new IOException(
                            "Tesseract failed. Exit code: "
                            + exitCode
                    );
                }

                allText
                        .append("\n--- Page ")
                        .append(page + 1)
                        .append(" ---\n")
                        .append(result);

                // =================================================
                // حساب الساعات
                // =================================================

                int pageHours =
                        calculatePassedHours(
                                imagePath.toFile()
                        );

                completedHours += pageHours;

                System.out.println(
                        "Passed hours on page "
                        + (page + 1)
                        + ": "
                        + pageHours
                );

            } finally {

                Files.deleteIfExists(imagePath);
            }
        }

        lastCompletedHours =
                completedHours;

        System.out.println(
                "TOTAL COMPLETED HOURS = "
                + completedHours
        );

        return allText.toString();
    }

    // =====================================================
    // تشغيل Tesseract TSV
    // =====================================================

    private static int calculatePassedHours(
            File imageFile)
            throws IOException, InterruptedException {

        ProcessBuilder processBuilder =
                new ProcessBuilder(
                        "tesseract",
                        imageFile.getAbsolutePath(),
                        "stdout",
                        "-l",
                        "ara+eng",
                        "--psm",
                        "6",
                        "tsv"
                );

        processBuilder.redirectErrorStream(true);

        Process process =
                processBuilder.start();

        String tsv =
                new String(
                        process.getInputStream()
                                .readAllBytes(),
                        StandardCharsets.UTF_8
                );

        int exitCode =
                process.waitFor();

        if (exitCode != 0) {

            throw new IOException(
                    "Tesseract TSV failed."
            );
        }

        return extractPassedHours(tsv);
    }

    // =====================================================
    // تمثيل كلمة OCR
    // =====================================================

    private static class OCRWord {

        int page;
        int block;
        int paragraph;
        int line;

        int left;
        int width;

        String text;

        OCRWord(
                int page,
                int block,
                int paragraph,
                int line,
                int left,
                int width,
                String text) {

            this.page = page;
            this.block = block;
            this.paragraph = paragraph;
            this.line = line;

            this.left = left;
            this.width = width;

            this.text = text;
        }

        int centerX() {

            return left + width / 2;
        }
    }

    // =====================================================
    // استخراج ساعات المواد
    // =====================================================

    private static int extractPassedHours(
            String tsv) {

        String[] lines =
                tsv.split("\\R");

        List<OCRWord> words =
                new ArrayList<>();

        // -------------------------------------------------
        // قراءة TSV
        // -------------------------------------------------

        for (int i = 1;
                i < lines.length;
                i++) {

            String[] columns =
                    lines[i].split(
                            "\\t",
                            -1
                    );

            if (columns.length < 12) {
                continue;
            }

            try {

                int level =
                        Integer.parseInt(
                                columns[0]
                        );

                // level 5 = word
                if (level != 5) {
                    continue;
                }

                int page =
                        Integer.parseInt(
                                columns[1]
                        );

                int block =
                        Integer.parseInt(
                                columns[2]
                        );

                int paragraph =
                        Integer.parseInt(
                                columns[3]
                        );

                int line =
                        Integer.parseInt(
                                columns[4]
                        );

                int left =
                        Integer.parseInt(
                                columns[6]
                        );

                int width =
                        Integer.parseInt(
                                columns[8]
                        );

                String word =
                        columns[11].trim();

                if (word.isEmpty()) {
                    continue;
                }

                words.add(
                        new OCRWord(
                                page,
                                block,
                                paragraph,
                                line,
                                left,
                                width,
                                word
                        )
                );

            } catch (Exception ignored) {
            }
        }

        // -------------------------------------------------
        // تجميع الكلمات حسب السطر
        // -------------------------------------------------

        int total = 0;

        for (int i = 0;
                i < words.size();
                i++) {

            OCRWord current =
                    words.get(i);

            /*
             * نبحث فقط في الجزء الأيمن من الجدول.
             *
             * عند الـ200 DPI:
             * Passed column تقريباً في الجهة اليمنى.
             */

            int x =
                    current.centerX();

            if (x < 1050) {
                continue;
            }

            if (x > 1600) {
                continue;
            }

            // -------------------------------------------------
            // تنظيف OCR
            // -------------------------------------------------

            String cleaned =
                    current.text
                            .replaceAll(
                                    "[^A-Za-z]",
                                    ""
                            )
                            .toUpperCase();

            /*
             * Tesseract ممكن يقرأ:
             *
             * P
             * Pp
             * PP
             * P.
             * P|
             */

            boolean passed =
                    cleaned.equals("P")
                    || cleaned.equals("PP");

            if (!passed) {
                continue;
            }

            // -------------------------------------------------
            // وجدنا P
            // -------------------------------------------------

            int hours =
                    findHours(
                            words,
                            current
                    );

            if (hours > 0) {

                total += hours;

                System.out.println(
                        "Found P -> "
                        + hours
                        + " hours"
                );
            }
        }

        return total;
    }

    // =====================================================
    // البحث عن ساعات المادة بنفس السطر
    // =====================================================

    private static int findHours(
            List<OCRWord> words,
            OCRWord passedWord) {

        int bestHours = 0;

        int bestDistance =
                Integer.MAX_VALUE;

        for (OCRWord word : words) {

            // لازم يكون نفس السطر
            if (word.page != passedWord.page) {
                continue;
            }

            if (word.block != passedWord.block) {
                continue;
            }

            if (word.paragraph
                    != passedWord.paragraph) {

                continue;
            }

            if (word.line
                    != passedWord.line) {

                continue;
            }

            int x =
                    word.centerX();

            /*
             * عمود Hours موجود قبل Grade
             * وفي المنطقة الوسطى من الجدول.
             */

            if (x < 750 || x > 1150) {
                continue;
            }

            String number =
                    word.text.replaceAll(
                            "[^0-9]",
                            ""
                    );

            if (number.isEmpty()) {
                continue;
            }

            try {

                int hours =
                        Integer.parseInt(number);

                if (hours < 1
                        || hours > 6) {

                    continue;
                }

                int distance =
                        Math.abs(
                                x - 950
                        );

                if (distance
                        < bestDistance) {

                    bestDistance =
                            distance;

                    bestHours =
                            hours;
                }

            } catch (Exception ignored) {
            }
        }

        return bestHours;
    }

    // =====================================================
    // Getters
    // =====================================================

    public static int getLastCompletedHours() {

        return lastCompletedHours;
    }

    public static int getRemainingHours() {

        return TOTAL_HOURS
                - lastCompletedHours;
    }

    public static double getProgress() {

        return (
                (double) lastCompletedHours
                / TOTAL_HOURS
        ) * 100.0;
    }
}