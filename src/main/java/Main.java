import exception.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите, разделяя пробелами, ваши данные:" +
                "Фамилию Имя Отчество датурождения номертелефона пол" +
                "Форматы данных:\n" +
                "фамилия, имя, отчество - строки\n" +
                "датарождения - строка формата dd.mm.yyyy\n" +
                "номертелефона - целое беззнаковое число без форматирования\n" +
                "пол - символ латиницей f или m.");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                saveDate(scanner.nextLine());
                System.out.println("Данные сохранены");
                break;
            } catch (NotAllDataEnteredException e) {
                System.out.println(e.getMessage());
            } catch (EnteredUnnecessaryDataException e) {
                System.out.println(e);
            }

        }

    }

    /**
     * Проверка и сохранение введенной строки
     * @param userData введенная строка
     */
    private static void saveDate(String userData) throws NotAllDataEnteredException, EnteredUnnecessaryDataException {
        String[] array = userData.split(" ");

        if (array.length < 6) {
            throw new NotAllDataEnteredException(userData, "\n\"Фамилию Имя Отчество датурождения номертелефона пол\"");
        } else if (array.length > 6) {
            StringBuilder message = new StringBuilder();
            for (int i = 5; i < array.length; i++) {
                message.append(array[i]);
                message.append(" ");
            }
            throw new EnteredUnnecessaryDataException(message.toString());
        }
        StringBuilder result = new StringBuilder();
        result.append("<");
        result.append(nameFormat(array[1], "Фамилия"));
        result.append("><");
        result.append(nameFormat(array[0], "Имя"));
        result.append("><");
        result.append(nameFormat(array[2], "Отчество"));
        result.append("><");
        try{
        result.append(dateFormat(array[3]));} catch (YearBirthdateException e) {
            System.out.println("Вы не можете пользоваться сервисом - вам нет 16 лет!");
            throw new RuntimeException(e);
        }
        result.append("> <");
        result.append(numberFormat(array[4]));
        result.append("><");
        result.append(genderFormat(array[5]));
        result.append(">");
        try(FileReader fileReader = new FileReader(array[1]);
            BufferedReader bufferedReader = new BufferedReader(fileReader)
        ){
            StringBuilder stringBuilder = new StringBuilder();
            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine());
                stringBuilder.append("\n");
            }
             String fileCurrent = stringBuilder.toString() + result;
            fileWrite(fileCurrent, array[1]);
        } catch (FileNotFoundException e) {
            fileWrite(result.toString(), array[1]);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод записи файла
     * @param fileCurrent данные для записи в файл
     * @param nameFile фамилия пользователя
     */
    private static void fileWrite(String fileCurrent, String nameFile) {
        try(FileWriter fileWriter = new FileWriter(nameFile)){
            fileWriter.write(fileCurrent);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод проверки пола
     * @param gender введенный пол
     * @return допустимый пол (f/m)
     */
    private static String genderFormat(String gender) {
        try {

            if (gender.equals("f") || gender.equals("m")) {
                return gender;
            } else {
                throw new GenderFormatException("Неправильный формат ввода пола (f/m)");
            }
        } catch (GenderFormatException e) {
            System.out.println(e.getMessage());
            System.out.println("Введите пол: ");
            return genderFormat(stringInput());
        }
    }

    /**
     * Метод проверки номера
     * @param number введенный номер
     * @return допустимый номер
     */
    private static String numberFormat(String number) {

        try {
            int f = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели недопустимые символы (допустимы только цифры!)" +
                    "\nВведите номер повторно: ");
            return numberFormat(stringInput());
        }
        return number;
    }

    /**
     * Метод проверки даты рождения
     * @param birthdate введенная дата рождения
     * @return допустимая дата рождения
     * @throws YearBirthdateException - пользователю нет 16 лет (в 2023 году)
     */
    private static String dateFormat(String birthdate) throws YearBirthdateException{
        int[] monthsBig = new int[]{1, 3, 5, 7, 8, 10, 12};
        String[] strings = birthdate.split("\\.");

        try {
            new SimpleDateFormat("mm.dd.yyyy").parse(birthdate);
            int[] date = new int[3];
            for (int i = 0; i < date.length; i++) {
                try {
                    date[i] = Integer.parseInt(strings[i]);

                } catch (NumberFormatException e) {
                    System.out.println("Вы ввели неправильную дату рождения:sdf ghsd f " + birthdate
                            + "\n Введите дату рождения в формате: DD.MM.YYYY");
                    return dateFormat(stringInput());
                }
            }

            if (date[1] > 12) {
                throw new DataFormatException();
            } else if (Arrays.asList(monthsBig).contains(date[1])) {
                if (date[0] > 31) {
                    throw new DataDayFormatException("Вы ввели неправильный день (в месяце не может быть больше 31 дня)\n " +
                            "Введите дату рождения заново: ");
                }
            } else if (date[1] == 2) {
                if (date[0] > 29) {
                    throw new DataFormatException("Вы ввели неправильный день (в этом месяце не может быть больше 29 дней)\n " +
                            "Введите дату рождения заново: ");
                }
            } else {
                if (date[0] > 30) {
                    throw new DataFormatException("Вы ввели неправильный день (в этом месяце не может быть больше 30 дней)\n " +
                            "Введите дату рождения заново: ");
                }
            }
            try {
                if (date[2] >= 2008) {
                    throw new YearBirthdateException("Вы не достигли 16 лет!");
                }
            } catch (YearBirthdateException e) {
                throw new RuntimeException(e);
            }

        } catch (DataDayFormatException e) {
            System.out.println(e.getMessage());
            dateFormat(stringInput());
        } catch (ParseException | DataFormatException e) {
            System.out.println("Вы ввели неправильную дату рождения: " + birthdate
                    + "\n Введите дату рождения в формате: DD.MM.YYYY");
            return dateFormat(stringInput());
        }
        return birthdate;
    }

    /**
     * Метод ввода данных (строчный)
     * @return введенная строка
     */
    private static String stringInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Метод проверки Ф. И. О.
     * @param name введенное значение
     * @param format передаваемое значения (Ф/И/О)
     * @return допустимое значение
     */
    private static String nameFormat(String name, String format) {
        try {
            if (name.length() < 3) {
                throw new LengthException(format, "короче 3 символов");
            }
            if (!name.matches("[a-zA-Zа-яА-Я]+")) {
                throw new TextFormatException(format);
            }
        } catch (LengthException | TextFormatException e) {
            System.out.println(e.getMessage());
            Scanner scanner = new Scanner(System.in);
            return nameFormat(scanner.nextLine(), format);
        }

        return name;
    }


}
