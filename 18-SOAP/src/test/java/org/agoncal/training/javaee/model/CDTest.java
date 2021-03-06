package org.agoncal.training.javaee.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author Antonio Goncalves
 *         Training - Beginning with The Java EE 7 Platform
 *         http://www.antoniogoncalves.org
 *         --
 */
public class CDTest {

    // ======================================
    // =             Attributes             =
    // ======================================

    private static ValidatorFactory vf;
    private static Validator validator;

    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    @BeforeClass
    public static void init() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @AfterClass
    public static void closeContainer() {
        if (vf != null) {
            vf.close();
        }
    }

    // ======================================
    // =             Unit tests             =
    // ======================================

    @Test
    public void shouldRaiseNoConstraintViolation() {

        // Creates a CD with null title
        CD cd = new CD("title", 12.80f, "Beatles master piece", "Apple", 1, 53.32f, "Pop");

        // Validate the cd
        Set<ConstraintViolation<CD>> constraints = validator.validate(cd);
        assertEquals(0, constraints.size());
    }

    @Test
    public void shouldRaiseConstraintsViolation() {

        // Creates a CD with null title
        CD cd = new CD();

        // Validate the cd
        Set<ConstraintViolation<CD>> constraints = validator.validate(cd);
        assertEquals(1, constraints.size());
    }

    @Test
    public void shouldRaiseWrongMusicCompany() throws Exception {

        // Creates a cd
        CD cd = new CD("St Pepper", 12.80f, "Beatles master piece", "apple", 1, 53.32f, "Pop");

        // Validate the cd
        Set<ConstraintViolation<CD>> constraints = validator.validate(cd);
        assertEquals(1, constraints.size());
    }

    @Test
    public void shouldRaiseTooManyCDs() throws Exception {

        // Creates a cd
        CD cd = new CD("St Pepper", 12.80f, "Beatles master piece", "Apple", 11, 53.32f, "Pop");

        // Validate the cd
        Set<ConstraintViolation<CD>> constraints = validator.validate(cd);
        assertEquals(1, constraints.size());
    }

    @Test
    public void shouldRaiseMusicGenreLowerCase() throws Exception {

        // Creates a cd
        CD cd = new CD("St Pepper", 12.80f, "Beatles master piece", "Apple", 11, 53.32f, "pop");

        // Validate the cd
        Set<ConstraintViolation<CD>> constraints = validator.validate(cd);
        assertEquals(2, constraints.size());
    }

    @Test
    public void shouldRaiseMusicGenreTooShort() {

        // Creates a cd
        CD cd = new CD("St Pepper", 12.80f, "Beatles master piece", "Apple", 11, 53.32f, "P");

        // Validate the cd
        Set<ConstraintViolation<CD>> constraints = validator.validate(cd);
        assertEquals(2, constraints.size());
    }

    @Test
    public void shouldRaiseMusicGenreTooLong() {

        // Creates a cd
        CD cd = new CD("St Pepper", 12.80f, "Beatles master piece", "Apple", 11, 53.32f, "Poooooooooooooooooooooooooooooooooooooooooooooooooooooop");

        // Validate the cd
        Set<ConstraintViolation<CD>> constraints = validator.validate(cd);
        assertEquals(2, constraints.size());
    }
}
