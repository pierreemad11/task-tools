package APP;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import EJB.Calculator;
import javax.ws.rs.core.MediaType;


@Stateless
@Path("calc")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class CalculatorService {
    @PersistenceContext
    private EntityManager em;

    @POST
    @Path("calculate")
    public Response calculate(Calculator calc) {
        try {
            int result = 0;
            switch (calc.getOperation()) {
                case "+":
                    result = calc.getNumber1() + calc.getNumber2();
                    break;
                case "-":
                    result = calc.getNumber1() - calc.getNumber2();
                    break;
                case "*":
                    result = calc.getNumber1() * calc.getNumber2();
                    break;
                case "/":
                    if (calc.getNumber2() == 0) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity("Cannot divide by zero.")
                                .build();
                    }
                    result = calc.getNumber1() / calc.getNumber2();
                    break;
                default:
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Invalid operation.")
                            .build();
            }

            // Set the result in the calc object
            calc.setResult(result);

            // Create a new Calculation entity and persist the result
            Calculator calculation = new Calculator();
            calculation.setNumber1(calc.getNumber1());
            calculation.setNumber2(calc.getNumber2());
            calculation.setOperation(calc.getOperation());
            calculation.setResult(result);
            
            em.persist(calculation);

            // Return the updated calc object as a JSON response
            return Response.ok(calc).build();

        } catch (Exception e) {
            // Handle any exceptions that might occur
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred during calculation.")
                    .build();
        }
    }
    @GET
    @Path("calculations")
    public Response getAllCalculations() {
        try {
            // Query the database to retrieve all calculations
            List<Calculator> calculations = em.createQuery("SELECT c FROM Calculator c", Calculator.class).getResultList();
            
            // Return the list of calculations as a JSON response
            return Response.ok(calculations).build();
        } catch (Exception e) {
            // Handle any exceptions that might occur
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving calculations.")
                    .build();
        }
    }
}