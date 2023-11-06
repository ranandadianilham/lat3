import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class index {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the console
        Scanner scanner = new Scanner(System.in);
        String filePath = "./data_sekolah.csv";
        String savePath = "./";

        String answer = initialPrompt(scanner);

        // Close the Scanner to release system resources (not mandatory, but good
        // practice)

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            Map<Integer, Integer> frequencyMap = new HashMap<>();
            ArrayList<Integer> values = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                for (int i = 1; i < fields.length; i++) {
                    int value = Integer.parseInt(fields[i]);
                    if (value < 6) {
                        frequencyMap.put(5, frequencyMap.getOrDefault(value, 0) + 1);
                    } else {
                        frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
                    }
                    values.add(value);
                }
            }

            // Calculate mean
            double sum = 0;
            for (int value : values) {
                sum += value;
            }
            double mean = sum / values.size();

            // Calculate median
            Collections.sort(values);
            int middle = values.size() / 2;
            double median;
            if (values.size() % 2 == 0) {
                median = (values.get(middle - 1) + values.get(middle)) / 2.0;
            } else {
                median = values.get(middle);
            }

            // Calculate modus
            int maxFrequency = 0;
            int modus = -1;
            for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                if (entry.getValue() > maxFrequency) {
                    maxFrequency = entry.getValue();
                    modus = entry.getKey();
                }
            }

            // Close the BufferedReader

            do {
                switch (answer) {
                    case "1":
                        answer = initialPrompt(scanner);
                        break;
                    case "2":
                        firstChoice(savePath, frequencyMap);
                        answer = returnToStart(scanner);
                        break;
                    case "3":
                        secondChoice(savePath, mean, median, modus);
                        answer = returnToStart(scanner);
                        break;
                    case "4":
                        firstChoice(savePath, frequencyMap);
                        secondChoice(savePath, mean, median, modus);
                        answer = returnToStart(scanner);
                        break;
                    case "0":
                        // answer = initialPrompt(scanner);
                        System.exit(0);
                        break;
                }
            } while (answer != null);

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Aplikasi Pengolah Nilai Siswa");
            System.out.println("File tidak ditemukan");
            System.out.println("0 exit");
            System.out.println("1 kembali ke menu utama");
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();

        // Print a greeting using the input
        // System.out.println("Hello, " + answer + "! Nice to meet you.");
    }

    static String initialPrompt(Scanner scanner) {
        // Prompt the user for their name

        System.out.println("Aplikasi Pengolah Nilai Siswa: ");
        System.out.println("Letakan file csv dengan nama file data_sekolah di direktori berikut: ");
        System.out.println("Berikut: C: kerjaan/idstar/file");

        // Read the user's input as a String
        System.out.println("Pilih menu:");
        System.out.println("1. Generate txt untuk menampilkan modus");
        System.out.println("2. genrate txt untuk menampilkan nilai rata-rata, median");
        System.out.println("3. generate kedua file");

        System.out.println("0. exit");

        String answer = scanner.nextLine();
        if (answer == "0")
            System.exit(0);

        answer = String.valueOf(Integer.parseInt(answer) + 1);
        return answer;
    }

    static void firstChoice(String savePath, Map<Integer, Integer> frequencyMap) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(savePath + "data_modus.txt"));

        try {
            writer.write("Nilai | Frekuensi");
            writer.newLine();
            writer.write("Kurang dari 6 | " + frequencyMap.getOrDefault(5, 0));
            writer.newLine();
            int max = 0;

            for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                }
                // System.out.println("i : " + entry.getKey() + " | v: " + entry.getValue());
            }

            for (int i = 6; i <= max; i++) {
                /*
                 * if (frequencyMap.getOrDefault(i, 0) < 6) {
                 * writer.write(i + "  | " + frequencyMap.getOrDefault(i, 0));
                 * } else {
                 * }
                 */
                if (frequencyMap.getOrDefault(i, 0) == 0)
                    continue;

                //System.out.println("i : " + i + " | val: " + frequencyMap.getOrDefault(i, 0));

                writer.write(i + "  | " + frequencyMap.getOrDefault(i, 0));

                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.close();
    }

    static void secondChoice(String savePath, Double mean, Double median, int modus) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(savePath + "data_modus_median.txt"));

        try {
            writer.write("Berikut Hasil Pengolahan Nilai: ");
            writer.newLine();
            writer.write("Berikut hasil sebaran data nilai");
            writer.newLine();

            writer.write("Mean: " + mean);
            writer.newLine();
            writer.write("Median: " + median);
            writer.newLine();
            writer.write("Modus: " + modus);
            writer.newLine();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        writer.close();
    }

    static String returnToStart(Scanner scanner) {
        System.out.println("Aplikasi Pengolah Nilai Siswa: ");
        System.out.println("File telah digenerate di:  C://temp/dir");
        System.out.println("0 exit");
        System.out.println("1 kembali ke menu utama");

        String answer = scanner.nextLine();
        return answer;
    }
}

// javac -cp ".;libs/poi-<version>.jar;libs/poi-ooxml-<version>.jar"
// ConvertTxtToDocx.java