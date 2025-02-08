package TopReports.Exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

public class GlobalExceptionError extends RuntimeException {



  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex) {
    return ResponseEntity.badRequest().body("Formato de data inválido. Use o formato 'yyyy-MM-dd'.");
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Ocorreu um erro no servidor.");
  }


  public GlobalExceptionError(String message) {
    super(message);
  }
}
