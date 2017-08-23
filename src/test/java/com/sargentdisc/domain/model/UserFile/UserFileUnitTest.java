package com.sargentdisc.domain.model.UserFile;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sargentdisc.domain.model.userfile.UserFile;




public class UserFileUnitTest{
	
	@Test
	public void should_build_userfile() throws Exception {
		UserFile file = UserFile.named("Thomas").withLocation("London").withText("Test").build();
		Assert.assertThat("Thomas",is(equalTo(file.getName())));
		Assert.assertThat("London",is(equalTo(file.getLocation())));
		Assert.assertThat("Test",containsString("Test"));
		
	}
	
	@Test
	public void should_build_userfile_with_mandatories() throws Exception {
		UserFile file = UserFile.named("Thomas").withLocation("London").build();
		Assert.assertNotNull(file);
		
	}
	
	@Test
	public void should_build_userfile_with_validations_ok() throws Exception {
		UserFile file = UserFile.named("Thomas").withLocation("London").build();
		Assert.assertNotNull(file);
		
	}
	
	@Test(expected = IllegalStateException.class)
	public void should_build_userfile_with_validations_failing_name_null() throws Exception {
		UserFile.named(null).withLocation("London").build();
		
	}	
	
	@Test(expected = IllegalStateException.class)
	public void should_build_userfile_with_validations_failing_name_with_space() throws Exception {
		UserFile.named("Tho mas").withLocation("London").build();
		
	}
	
	@Test(expected = IllegalStateException.class)
	public void should_build_userfile_with_validations_failing_name_with_big_length() throws Exception {
		UserFile.named("Thomasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss").withLocation("London").build();
	}	
	
	@Test(expected = IllegalStateException.class)
	public void should_build_userfile_with_validations_failing_location_null() throws Exception {
		UserFile.named("Thomas").withLocation(null).build();
		
	}		
	
	@Test(expected = IllegalStateException.class)
	public void should_build_userfile_with_validations_failing_location_with_big_length() throws Exception {
		UserFile.named("Thomas").withLocation("Londonnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"
				+ "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn").build();
	}	
	
	@Test
	public void should_do_nothing_when_removing_commas() throws Exception {
		UserFile file = UserFile.named("Thomas").withLocation("London").build();
		file.removeCommasWithSpace();
		Assert.assertNull(file.getText());
	}		
	
	@Test
	public void should_remove_commas_with_spaces_from_text() throws Exception {
		String sbJSON = loadFile();
		UserFile userFile = new ObjectMapper().readValue(sbJSON, UserFile.class);
		String textBefore = userFile.getText();
		userFile.removeCommasWithSpace();
		Assert.assertFalse(textBefore.equals(userFile.getText()));
		Assert.assertFalse(userFile.getText().contains(", "));
	}

	private String loadFile() {
		File file = new File(this.getClass().getClassLoader().getResource("DocumentTest.txt").getFile());
		StringBuffer sbJSON = new StringBuffer();
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				sbJSON.append(line).append("\n");
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sbJSON.toString();
	}	

}
