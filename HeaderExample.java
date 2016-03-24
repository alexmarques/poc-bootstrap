import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class HeaderExample {

	public class HeaderExamplePageEvent extends PdfPageEventHelper {
		
		private PdfTemplate template;

		public void onEndPage(PdfWriter writer, Document document) {
			
			PdfContentByte cb = writer.getDirectContent();
	        cb.saveState();
	        String text = "Page " + writer.getPageNumber() + " of ";
	        
	        float textBase = document.bottom() - 20;
	        
	        cb.beginText();
	        try {
				cb.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, "UTF-8", true), 12);
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        cb.setTextMatrix((document.right() / 2), textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(template, (document.right() / 2), textBase);
			
			
			Phrase nome = new Phrase("Nome", FontFactory.getFont(BaseFont.HELVETICA, 12, Font.BOLD));
			Phrase funcional = new Phrase("Funcional", FontFactory.getFont(BaseFont.HELVETICA, 12, Font.BOLD));
			Phrase gestor = new Phrase("Gestor", FontFactory.getFont(BaseFont.HELVETICA, 12, Font.BOLD));
			Phrase anoBase = new Phrase("Ano Base", FontFactory.getFont(BaseFont.HELVETICA, 12, Font.BOLD));
			Phrase status = new Phrase("Status", FontFactory.getFont(BaseFont.HELVETICA, 12, Font.BOLD));


			cb.moveTo(20, PageSize.A4.height() - 20);
			cb.lineTo(PageSize.A4.width() - 20, PageSize.A4.height() - 20);

			cb.moveTo(20, PageSize.A4.height() - 120);
			cb.lineTo(PageSize.A4.width() - 20, PageSize.A4.height() - 120);

			cb.moveTo(20, PageSize.A4.height() - 20);
			cb.lineTo(20, PageSize.A4.height() - 120);

			cb.moveTo(PageSize.A4.width() - 20, PageSize.A4.height() - 20);
			cb.lineTo(PageSize.A4.width() - 20, PageSize.A4.height() - 120);

			cb.stroke();

			try {
				ColumnText ct = new ColumnText(writer.getDirectContent());
				ct.setSimpleColumn(nome, 36, 36, PageSize.A4.width() - 36, PageSize.A4.height() - 36, 0,
						PdfPCell.ALIGN_LEFT);
				ct.go();
				ct.setSimpleColumn(new Phrase("Eliana de Carvalho Palmieri"), 100, 36, PageSize.A4.width() - 36,
						PageSize.A4.height() - 36, 0, PdfPCell.ALIGN_LEFT);
				ct.go();
				ct.setSimpleColumn(funcional, 36, 55, PageSize.A4.width() - 36, PageSize.A4.height() - 55, 0,
						PdfPCell.ALIGN_LEFT);
				ct.go();
				ct.setSimpleColumn(new Phrase("999999999"), 100, 55, PageSize.A4.width() - 36,
						PageSize.A4.height() - 55, 0, PdfPCell.ALIGN_LEFT);
				ct.go();
				ct.setSimpleColumn(gestor, 36, 74, PageSize.A4.width() - 36, PageSize.A4.height() - 74, 0,
						PdfPCell.ALIGN_LEFT);
				ct.go();
				ct.setSimpleColumn(new Phrase("Angélica Simioni"), 100, 74, PageSize.A4.width() - 36,
						PageSize.A4.height() - 74, 0, PdfPCell.ALIGN_LEFT);
				ct.go();
				ct.setSimpleColumn(anoBase, 36, 93, PageSize.A4.width() - 36, PageSize.A4.height() - 93, 0,
						PdfPCell.ALIGN_LEFT);
				ct.go();
				ct.setSimpleColumn(new Phrase("2015"), 100, 93, PageSize.A4.width() - 36, PageSize.A4.height() - 93, 0,
						PdfPCell.ALIGN_LEFT);
				ct.go();
				ct.setSimpleColumn(status, 36, 112, PageSize.A4.width() - 36, PageSize.A4.height() - 112, 0,
						PdfPCell.ALIGN_LEFT);
				ct.go();
				ct.setSimpleColumn(new Phrase("Em Andamento"), 100, 112, PageSize.A4.width() - 36,
						PageSize.A4.height() - 112, 0, PdfPCell.ALIGN_LEFT);
				ct.go();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		
		public void onOpenDocument(PdfWriter writer, Document document) {
			template = writer.getDirectContent().createTemplate(PageSize.A4.width(), PageSize.A4.height());
			template.setBoundingBox(new Rectangle(-20, -20, 100, 100));
		}
		
		public void onCloseDocument(PdfWriter writer, Document document) {
			template.beginText();
	        try {
				template.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, "UTF-8", true), 12);
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        template.setTextMatrix(55, 0);
	        template.showText(String.valueOf(writer.getPageNumber() - 1));
	        template.endText();
		}
		
		
	}

	public static void main(String[] args) throws Exception {
		new HeaderExample().createPdf();
	}

	public void createPdf() throws Exception {

		Document document = new Document(PageSize.A4, 36, 36, 120, 36);

		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("teste_header.pdf"));

		writer.setPageEvent(new HeaderExamplePageEvent());

		document.open();
		
		document.add(new Paragraph("Hello World!"));
		
		document.newPage();
		
		document.add(new Paragraph("Hello World!"));
		
		document.close();
	}
}
