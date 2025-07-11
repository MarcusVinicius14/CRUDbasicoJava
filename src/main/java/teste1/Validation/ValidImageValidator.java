package teste1.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ValidImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png");


    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private static final Tika tika = new Tika();

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        // Se o arquivo for opcional e não for enviado, a validação passa.
        if (file == null || file.isEmpty()) {
            return true;
        }

        String detectedType;
        try {
            detectedType = tika.detect(file.getInputStream());
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo para validação: " + e.getMessage());
            return false;
        }

        // Validação por magic bytes
        // Comparamos o tipo detectado pelo Tika com nossa lista de tipos permitidos.
        if (!ALLOWED_CONTENT_TYPES.contains(detectedType)) {
            return false;
        }

        // Validação de tamanho
        if (file.getSize() > MAX_FILE_SIZE) {
            return false;
        }

        return true;
    }
}