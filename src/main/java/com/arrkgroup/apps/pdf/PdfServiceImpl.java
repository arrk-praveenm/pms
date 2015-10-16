package com.arrkgroup.apps.pdf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arrkgroup.apps.model.AssesseesAssessor;

@Service("PdfService")
public class PdfServiceImpl implements PdfService {

	@Autowired
	PdfDaoImpl pdfDaoImpl;
	@Override
	public List<AssesseesAssessor> getAssesseesAssessorByCycle(int employeeId,
			int cycleId) {
		// TODO Auto-generated method stub
		return pdfDaoImpl.getAssesseesAssessorByCycle(employeeId, cycleId);
	}

}
