package med.xuxao.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import med.xuxao.api.domain.consultas.validacoes.ValidacoesAgendamento;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity notFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity invalidArguments(MethodArgumentNotValidException ex){
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(DadosErrosValidacao::new).toList());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity validationError(ValidationException ex){
        var errors = ex.getMessage();
        return ResponseEntity.badRequest().body(errors);
    }

    private record DadosErrosValidacao (String Nome, String Message){
        public DadosErrosValidacao(FieldError erro){
            this(erro.getField(),erro.getDefaultMessage());
        }
    }
}
