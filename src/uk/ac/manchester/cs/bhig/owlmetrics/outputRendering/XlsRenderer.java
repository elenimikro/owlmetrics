package uk.ac.manchester.cs.bhig.owlmetrics.outputRendering;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.Cell;
import jxl.LabelCell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author elenimikroyannidi
 * 
 *         Class responsible for creating and parsing an excel file.
 * 
 */
public class XlsRenderer {

	public static WritableSheet sheet1;
	public WritableSheet sheet2;
	public WritableWorkbook workbook;
	public WritableCellFormat bold;

	
//	public int row=0;
//	public int column=0;
	
	/**
	 * Creates an Xls file
	 * 
	 * @param filename
	 */
	public void createExcelFile(String filename) {
		try {
			// String filename = "input.xls";
			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("en", "EN"));
			workbook = Workbook.createWorkbook(new File( filename), ws);
			sheet1 = workbook.createSheet("Sheet 1", 0);
			sheet2 = workbook.createSheet("Sheet 2", 1);
			// Create create a bold font with unterlines
			WritableFont boldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false);
			bold = new WritableCellFormat(boldFont);
			
		} catch (IOException e) {
			System.out.println("Error when creating the excel file "
					+ e.toString());
			e.printStackTrace();
		}

	}// createExcelFile(

	/**
	 * Initialise the sheet of the xls file
	 * 
	 * @param the
	 *            sheet that will be initialised
	 */
	public void initialiseDataSheet(WritableSheet sheet) {
		try {
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);
			WritableCellFormat cf = new WritableCellFormat(wf);
			cf.setWrap(true);
		} catch (WriteException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Creates a label in the excel sheet.
	 * 
	 * @param column
	 *            The column of the cell
	 * @param row
	 *            The row of the cell
	 * @param label
	 *            The label that will be written in the cell
	 * @param sheet
	 *            The sheet of the xls file
	 */
	public void createExcelLabel(int column, int row, String label,
			WritableSheet sheet) {
		try {
			/* Creates Label and writes a string to one cell of sheet */
			Label l = new Label(column, row, label);

			sheet.addCell(l);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}// createExcelLabel(int column, int row, String label)

	/**
	 * Creates a caption in the excel sheet.
	 * 
	 * @param column
	 *            The column of the cell
	 * @param row
	 *            The row of the cell
	 * @param label
	 *            The label that will be written in the cell
	 * @param sheet
	 *            The sheet of the xls file
	 */
	public void createExcelCaption(int column, int row, String label,
			WritableSheet sheet) {
		try {
			/* Creates Label and writes a string to one cell of sheet */
			Label l = new Label(column, row, label, bold);
			sheet.addCell(l);
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// createExcelCaption(int column, int row, String label)

	/**
	 * Finds a label in the excel sheet.
	 * 
	 * @param label
	 * @return the LabelCell that refers to the label. Null in case the label
	 *         wasn't found in the xls sheet.
	 */
	public LabelCell findLabelinExcel(String label) {
		LabelCell lc = sheet1.findLabelCell(label);
		//LabelCell lc = sheet1.findLabelCell(label);
		if (lc != null) {
			System.out.println(lc.getContents() + " was found!");
		}

		return lc;
	}// findLabelinExcel(String label)
	
	public void closeXlsFile(){
		try {
			workbook.close();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeXls() {
		try {
			workbook.write();
		} catch (IOException e) {
			System.out.println("Error when writing the excel file "
					+ e.toString());
			e.printStackTrace();
		}
	}
	

//	public void writeMetricInExcel(String description, String value) {
//		createExcelLabel(0, row, description, sheet1);
//		createExcelLabel(column, row, value, sheet1);
//		incrementRow();
//	}
//
//	public void incrementRow() {
//		row++;
//	}
//
//	
//	public void createMetricCaption(String tag) {
//		createExcelCaption(0, row, tag, sheet1);
//		incrementRow();
//	}
//
//	public void createExcelColumnCaption(int columnNo, String caption) {
//		createExcelCaption(columnNo, row, caption, sheet1);
//		incrementRow();
//		
//	}
	
	
	


}
