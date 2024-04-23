package EJB;

import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Stateless
@Entity
@Table(
   name = "calculations"
)
public class Calculator {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Long id;
   @Column(
      nullable = false
   )
   public int number1;
   @Column(
      nullable = false
   )
   public int number2;
   @Column(
      nullable = false
   )
   public String operation;
   @Column(
      nullable = false
   )
   public int result;

   public Calculator() {
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public int getNumber1() {
      return this.number1;
   }

   public void setNumber1(int number1) {
      this.number1 = number1;
   }

   public int getNumber2() {
      return this.number2;
   }

   public void setNumber2(int number2) {
      this.number2 = number2;
   }

   public String getOperation() {
      return this.operation;
   }

   public void setOperation(String operation) {
      this.operation = operation;
   }

   public int getResult() {
      return this.result;
   }

   public void setResult(int result) {
      this.result = result;
   }
}