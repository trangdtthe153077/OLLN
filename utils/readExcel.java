/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dal.QuestionDAO;
import java.util.ArrayList;
import model.Question;
import model.AnswerOption;
import model.QLevel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DELL
 */
public class readExcel {

    public static final int COLUMN_INDEX_CONTENT = 0;
    public static final int COLUMN_INDEX_LEVEL = 1;
    public static final int COLUMN_INDEX_OPTION_1 = 2;
    public static final int COLUMN_INDEX_OPTION_2 = 3;
    public static final int COLUMN_INDEX_OPTION_3 = 4;
    public static final int COLUMN_INDEX_OPTION_4 = 5;
    public static final int COLUMN_INDEX_OPTION_5 = 6;

    public ArrayList<Question> getListQS(String filename) {
        ArrayList<Question> list = new ArrayList<>();
        try {
//            File file = new File("H:\\"+filename);
            File file = new File("H:\\iter3\\Online-Learning-SWP\\web\\upload\\import\\"+filename);
            FileInputStream fis = new FileInputStream(file);
            Workbook wb = getWorkbook(fis, file.toString());
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            while (itr.hasNext()) {
                Row row = itr.next();
                if (row.getRowNum() == 0) {
                    continue; // header
                }
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
                Question q = new Question();
                ArrayList<AnswerOption> answers = new ArrayList<>();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    Object cellValue = getCellValue(cell);
                    if (cellValue == null || cellValue.toString().isEmpty()) {
                        continue;
                    }
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        case COLUMN_INDEX_CONTENT:
                            q.setContent((String) cellValue);
                            break;
                        case COLUMN_INDEX_LEVEL:
                            QuestionDAO qsDB = new QuestionDAO();
                            String name = (String) cellValue;
                            int id_level = qsDB.getIDLevelByName(name);
                            QLevel level = new QLevel(id_level, name);
                            q.setLevel(level);
                            break;
                        case COLUMN_INDEX_OPTION_1:
                        case COLUMN_INDEX_OPTION_2:
                        case COLUMN_INDEX_OPTION_3:
                        case COLUMN_INDEX_OPTION_4:
                        case COLUMN_INDEX_OPTION_5:
                            AnswerOption a = new AnswerOption();
                            String option = (String) cellValue;
                            if (option.startsWith("#")) {
                                a.setOption(option.substring(1));
                                a.setIsTrue(true);
                            } else {
                                a.setOption(option);
                                a.setIsTrue(false);
                            }
                            answers.add(a);
                            break;
                        default:
                            break;
                    }
                    q.setAnswers(answers);
                }
//                System.out.println("");
                list.add(q);
            }
            wb.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static void main(String[] args) {
        readExcel re = new readExcel();
        ArrayList<Question> list = re.getListQS("Quiz1.xlsx");
        for (Question question : list) {
            System.out.println(question.toString());
        }
    }
    
//    public static void main(String[] args) {
//        ArrayList<Question> list = new ArrayList<>();
//        try {
//            File file = new File("H:\\template QS.xlsx");
//            FileInputStream fis = new FileInputStream(file);
//            Workbook wb = getWorkbook(fis, file.toString());
//            Sheet sheet = wb.getSheetAt(0);
//            Iterator<Row> itr = sheet.iterator();
//            while (itr.hasNext()) {
//                Row row = itr.next();
//                if (row.getRowNum() == 0) {
//                    continue; // header
//                }
//                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
//                Question q = new Question();
//                ArrayList<AnswerOption> answers = new ArrayList<>();
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    Object cellValue = getCellValue(cell);
//                    if (cellValue == null || cellValue.toString().isEmpty()) {
//                        continue;
//                    }
//                    int columnIndex = cell.getColumnIndex();
//                    switch (columnIndex) {
//                        case COLUMN_INDEX_CONTENT:
//                            q.setContent((String) cellValue);
//                            break;
//                        case COLUMN_INDEX_LEVEL:
//                            QuestionDAO qsDB = new QuestionDAO();
//                            String name = (String) cellValue;
//                            int id_level = qsDB.getIDLevelByName(name);
//                            QLevel level = new QLevel(id_level, name);
//                            q.setLevel(level);
//                            break;
//                        case COLUMN_INDEX_OPTION_1:
//                        case COLUMN_INDEX_OPTION_2:
//                        case COLUMN_INDEX_OPTION_3:
//                        case COLUMN_INDEX_OPTION_4:
//                        case COLUMN_INDEX_OPTION_5:
//                            AnswerOption a = new AnswerOption();
//                            String option = (String) cellValue;
//                            if (option.startsWith("#")) {
//                                a.setOption(option.substring(1));
//                                a.setIsTrue(true);
//                            } else {
//                                a.setOption(option);
//                                a.setIsTrue(false);
//                            }
//                            answers.add(a);
//                            break;
//                        default:
//                            break;
//                    }
//                    q.setAnswers(answers);
//                }
////                System.out.println("");
//                list.add(q);
//            }
//            wb.close();
//            fis.close();
//            System.out.println(list.size());
//            for (Question q : list) {
//                System.out.println(q.toString());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static Object getCellValue(Cell cell) {
        Object cellValue = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
            case Cell.CELL_TYPE_ERROR:
                break;
            default:
                break;
        }
        return cellValue;
    }

    private static Workbook getWorkbook(InputStream inputStream, String file) throws IOException {
        if (file.endsWith("xlsx")) {
            return new XSSFWorkbook(inputStream);
        } else if (file.endsWith("xls")) {
            return new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
    }
}
