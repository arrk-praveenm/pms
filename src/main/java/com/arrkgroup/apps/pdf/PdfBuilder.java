package com.arrkgroup.apps.pdf;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arrkgroup.apps.form.SectionConsolidatedBean;
import com.arrkgroup.apps.model.Section;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 *
 * @author www.codejava.net
 *
 */
public class PdfBuilder extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<Section> allSections = (List<Section>) model.get("allSections");
		List allSectionAssessmentScore = (List) model
				.get("allSectionAssessmentScore");

		String relativeWebPath = "/resources/images/arrklogo.png";
		String absoluteDiskPath = getServletContext().getRealPath(
				relativeWebPath);
		Image arrkLogo = Image.getInstance(absoluteDiskPath);
		arrkLogo.scaleAbsolute(150f, 60f);
		doc.add(arrkLogo);

		doc.add(new Paragraph("Organisation weightage of different parameters"));

		// write table row data
		for (Section section : allSections) {
			doc.add(new Paragraph(section.getSection() + " - "
					+ section.getSection_weightage()));

		}


		//setting summary Ratings
		doc.add(setSummaryDetail(allSectionAssessmentScore));






	}


	protected PdfPTable setSummaryDetail(List allSectionAssessmentScore) throws DocumentException
	{
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 6.0f, 5.0f, });
		table.setSpacingBefore(10);

		// define font for table header row
				Font font = FontFactory.getFont(FontFactory.HELVETICA);
				font.setColor(BaseColor.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);

		// write table header
		cell.setPhrase(new Phrase("Section Title", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Section Score", font));
		table.addCell(cell);
		ListIterator allSectionAssessmentScoreIterator = allSectionAssessmentScore
				.listIterator();
		System.out.println(allSectionAssessmentScore.size());

		for (int i = 1; allSectionAssessmentScoreIterator.hasNext(); i++) {

			List<SectionConsolidatedBean> listSectionConsolidatedBean = (List) allSectionAssessmentScoreIterator
					.next();
			System.out.println("test"+ listSectionConsolidatedBean.size());

			ListIterator list = listSectionConsolidatedBean.listIterator();
			for (int j = 1; list.hasNext(); j++) {
				SectionConsolidatedBean sectionConsolidatedBean = (SectionConsolidatedBean) list
						.next();
				table.addCell(sectionConsolidatedBean.getSection());
				table.addCell(String.valueOf((sectionConsolidatedBean)
						.getSection_point()
						/ listSectionConsolidatedBean.size()));

				System.out.println("table row created");
			}
		}


		return table;
	}

}
