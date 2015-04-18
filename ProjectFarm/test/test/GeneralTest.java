package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.List;

import model.Category;
import model.Document;
import model.Evaluation;
import model.Evaluator;
import model.Owner;
import model.Project;
import model.db.CategoryDB;
import model.db.ProjectDB;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;

import org.junit.Test;

public class GeneralTest {

	@Test
	public void testCategory() {
		Category c = new Category("Test",1);
		assertEquals("Test", c.getDescription());
	}

	@Test
	public void testProject() {
		Category c = new Category("Test",1);
		Owner o = new Owner("a@a", "ma", "12",0);
		try {
			new Project("AA", "BB", 10, 100,"", o, c,1);
			new Project("AA", "BB", 1, 1,"", o, c,1);
		} catch (InvalidDataException e) {
			fail("error in project");
		}

		try {
			new Project(null, "BB", 1, 1,"", o, c,1);
			fail("project allowed null acronym");
		} catch (InvalidDataException e) {
		}

		try {
			new Project("AA", null, 1, 1,"", o, c,1);
			fail("project allowed null description");
		} catch (InvalidDataException e) {
		}

		try {
			new Project("AA", "DD", -1, 1,"", o, c,1);
			fail("project allowed negative funding period");
		} catch (InvalidDataException e) {
		}

		try {
			new Project("AA", "DD", 1, -1,"", o, c,1);
			fail("project allowed negative budget");
		} catch (InvalidDataException e) {
		}

		try {
			new Project("AA", "DD", 1, 1,"", null, c,1);
			fail("project allowed null owner ");
		} catch (InvalidDataException e) {
		}

		try {
			new Project("AA", "DD", 1, 1,"", o, null,1);
			fail("project allowed null category ");
		} catch (InvalidDataException e) {
		}

	}

	@Test
	public void testEvaluation() {

		Evaluator ev = new Evaluator("x@x", "x", "y",0);
		try {
			new Evaluation(null, 1, 1,1);
			fail("accepted null evaluator");
		} catch (InvalidDataException e) {
		}

		try {
			new Evaluation(ev, 0, 1,1);
			fail("accepted invalid attractiveness range");
		} catch (InvalidDataException e) {
		}

		try {
			new Evaluation(ev, 6, 1,1);
			fail("accepted invalid attractiveness range");
		} catch (InvalidDataException e) {
		}

		try {
			new Evaluation(ev, 1, 0,1);
			fail("accepted invalid risk range");
		} catch (InvalidDataException e) {
		}

		try {
			new Evaluation(ev, 5, 6,1);
			fail("accepted invalid risk range");
		} catch (InvalidDataException e) {
		}

		Category ca = new Category("Test",1);
		Owner ow = new Owner("a@a", "ma", "12",0);
		try {
			Project po = new Project("AA", "BB", 10, 100,"", ow, ca,1);
			Evaluation e = new Evaluation(ev, 5, 5,1);
			po.addEvaluation(e);
			assertEquals(po.getEvaluations().size(), 1);
			assertEquals(po.getEvaluations().get(0), e);
		} catch (InvalidDataException e) {
			fail("error creating project");
		}

	}

	@Test
	public void testDocument() {

		URL location = this.getClass().getProtectionDomain().getCodeSource()
				.getLocation();

		try {
			new Document(0, null, location.getFile() + "/model/Project.class", null, null);
		} catch (model.exception.InvalidDataException e) {
			fail("error creating document");
		}

		try {
			new Document(0, null, location.getFile() + "/model", null, null);
			fail("invalid document path");
		} catch (model.exception.InvalidDataException e) {

		}

		try {
			new Document(0, null, location.getFile() + "/helo", null, null);
			fail("invalid document path");
		} catch (model.exception.InvalidDataException e) {

		}

		Category ca = new Category("Test",1);
		Owner ow = new Owner("a@a", "ma", "12",0);
		try {
			Project po = new Project("AA", "BB", 10, 100,"", ow, ca,1);
			Document doc = new Document(0, null, location.getFile()
					+ "/model/Project.class", null, null);
			po.addDocument(doc);
			assertEquals(po.getDocuments().size(), 1);
			assertEquals(po.getDocuments().get(0), doc);
		} catch (InvalidDataException e) {
			fail("error creating project");
		}
	}

	@Test
	public void testCategoriesDB() {
		try {
			List<Category> cats = CategoryDB.getCategories();
			if (cats.size() == 0) {
				fail("empty category list");
			}
		} catch (DatabaseAccessError e) {
			fail("Error accessing db");
		}
	}

	@Test
	public void testUserDB() {
		try {
			if (UserDB.checkLogin("george@geek.com", "4456") == true) {
				fail("error checking password");
			}
		} catch (DatabaseAccessError e) {
			fail("database error");
		}

		try {
			if (UserDB.checkLogin("george@geek.com", "456") == false) {
				fail("error checking password");
			}
		} catch (DatabaseAccessError e) {
			fail("database error");
		}

		try {
			Owner o = UserDB.getOwner("john@acme.com");
			if (o == null) {
				fail("error retrieving owner");
			}
		} catch (DatabaseAccessError e) {
			fail("database error");

		}

		try {
			Evaluator e = UserDB.getEvaluator("sarah@geek.com");
			if (e == null) {
				fail("error retrieving evaluator");
			}
		} catch (DatabaseAccessError e) {
			fail("database error");

		}

		try {
			Owner o = UserDB.getOwner("sarah@geek.com");
			if (o != null) {
				fail("error retrieving owner");
			}
		} catch (DatabaseAccessError e) {
			fail("database error");

		}

		try {
			Evaluator e = UserDB.getEvaluator("john@acme.com");
			if (e != null) {
				fail("error retrieving evaluator");
			}
		} catch (DatabaseAccessError e) {
			fail("database error");

		}

	}

	@Test
	public void testProjectDB() {

		Owner o;
		try {
			o = UserDB.getOwner("john@acme.com");
			Category c = CategoryDB.getCategories().get(0);
			Project p = new Project("A", "B", 10, 1,"", o, c,1);
			
			ProjectDB.saveProject(p);
			Project x = ProjectDB.getProject(1);
			assertEquals(x,p);
			
			List<Project> ps = ProjectDB.getProjectsOfOwner(o);
			assertEquals(ps.get(0),p);
			
			ps = ProjectDB.getProjectsOfOwner(UserDB.getOwner("paul@acme.com"));
			if(ps.size() != 0) {
				fail("error getting projects");
			}
			
			
			List<Project> ps2 = ProjectDB.getAllProjects();
			assertEquals(ps2.get(0),p);
		} catch (DatabaseAccessError | InvalidDataException e) {
			fail("eror creating project");
		}

	}

}
