package TopReports.Exeption;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {




    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(500).body("Erro interno do servidor: " + ex.getMessage());
    }

    //@ExceptionHandler(Http403ForbiddenException.class)
    //@ResponseStatus(HttpStatus.FORBIDDEN)
    //public ResponseEntity<String> handleForbiddenException(Http403ForbiddenException ex) {
        //return ResponseEntity
             //   .status(HttpStatus.FORBIDDEN)
              //  .body("Acesso negado: você não possui permissão para acessar este recurso.");
  //  }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        ex.getConstraintViolations().forEach(violation -> {
            System.out.println("Violation: " + violation.getMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação: " + ex.getMessage());
    }




}
