package wcci.org.pawsclaws.DTO;

public class ErrorDataDTO {
    private String errorMessage;
    private int errorCode = 0;

    
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public int getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
