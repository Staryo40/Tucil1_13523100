package custom;
import custom.InputPuzzlerPro;

public class InputFormat {
    public InputPuzzlerPro input;
    public String errorMessage;

    public InputFormat(InputPuzzlerPro input, String errorMessage) {
        this.input = input;
        this.errorMessage = errorMessage;
    }

    public InputPuzzlerPro getInput() {
        return input;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

