package teste1.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD}) // Onde a anotação pode ser usada
@Retention(RetentionPolicy.RUNTIME) // Quando a anotação será avaliada
@Constraint(validatedBy = ValidImageValidator.class) // Qual classe contém a lógica de validação
public @interface ValidImage {
    // Mensagem de erro padrão
    String message() default "Arquivo de imagem inválido. O arquivo deve ser do tipo JPG/JPEG ou PNG e ter no máximo 10MB.";

    // Padrões da especificação de validação
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}