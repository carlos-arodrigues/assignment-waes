package com.waes.rodrigues.carlos.assignment;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.waes.rodrigues.carlos.assignment.domain.model.diff.Diff;
import com.waes.rodrigues.carlos.assignment.domain.model.diff.DiffException;
import com.waes.rodrigues.carlos.assignment.dto.DiffDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AssignmentWaesApplicationTests.class})
public class DiffUnitTest extends DiffTest{
	
	private Diff diff;
	String carlos = createJSON("Carlos",37);
	String silvana = createJSON("Silvana", 39);
	String silva = createJSON("Silva", 39);
	String silvan = createJSON("Silvan", 39);
	
	@After
	public void after(){
		diff = null;
	}
	
	@Test
	public void isEquals(){
		DiffDTO diffDTO = new DiffDTO(1L, carlos, carlos); 
		diff = new Diff(diffDTO);
		Diff diffCompare = new Diff(diffDTO);
		Assert.assertTrue(diff.equals(diffCompare));
		Assert.assertTrue(diff.hashCode() == diffCompare.hashCode());
		
	}	
	
	@Test
	public void isSameValues(){
		DiffDTO diffDTO = new DiffDTO(1L, carlos, carlos); 
		diff = new Diff(diffDTO);
		Assert.assertTrue(diff.isSameValues());
		
	}
	
	@Test
	public void isNotSameValues(){
		DiffDTO diffDTO = new DiffDTO(1L, carlos, silvana); 
		diff = new Diff(diffDTO);
		Assert.assertFalse(diff.isSameValues());
		
	}	
	
	@Test
	public void isSameEncoded() throws DiffException{
		DiffDTO diffDTO = new DiffDTO(1L, carlos, carlos); 
		diff = new Diff(diffDTO);
		Assert.assertTrue(diff.isSameEncoded());
		
	}
	
	@Test
	public void isNotSameEncoded() throws DiffException{
		DiffDTO diffDTO = new DiffDTO(1L, carlos, silva); 
		diff = new Diff(diffDTO);
		Assert.assertFalse(diff.isSameEncoded());
	}
	
	@Test(expected=DiffException.class)
	public void isSameEncodedException() throws DiffException{
		DiffDTO diffDTO = new DiffDTO(1L, null, silva); 
		diff = new Diff(diffDTO);
		diff.isSameEncoded();
	}		
	
	@Test
	public void isSameSize() throws DiffException{
		DiffDTO diffDTO = new DiffDTO(1L, carlos, silvan); 
		diff = new Diff(diffDTO);
		Assert.assertTrue(diff.isSameSize());
		
	}		
	
	@Test
	public void isNotSameSize() throws DiffException{
		DiffDTO diffDTO = new DiffDTO(1L, carlos, silvana); 
		diff = new Diff(diffDTO);
		Assert.assertFalse(diff.isSameSize());
		
	}		
	
	
	@Test
	public void isValidStates() throws DiffException{
		DiffDTO diffDTO = new DiffDTO(1L, carlos, silvan); 
		diff = new Diff(diffDTO);
		diff.validateConditionsToInsight();
	}		
	
	@Test(expected=DiffException.class)
	public void isNotValidStateLeft() throws DiffException{
		DiffDTO diffDTO = new DiffDTO(1L, null, silvan); 
		diff = new Diff(diffDTO);
		diff.validateConditionsToInsight();
	}		
	
	@Test(expected=DiffException.class)
	public void isNotValidStateRight() throws DiffException{
		DiffDTO diffDTO = new DiffDTO(1L, carlos, null); 
		diff = new Diff(diffDTO);
		diff.validateConditionsToInsight();
	}		

	@Test(expected=DiffException.class)
	public void isNotValidState() throws DiffException{
		DiffDTO diffDTO = new DiffDTO(1L, carlos, carlos); 
		diff = new Diff(diffDTO);
		diff.validateConditionsToInsight();
	}		
	
	@Test
	public void getInsight() throws DiffException{
		DiffDTO diffDTO = new DiffDTO(1L, carlos, silvan); 
		diff = new Diff(diffDTO);
		String insight = diff.getInsight();
		Assert.assertTrue(!insight.isEmpty());
		
	}		

}
