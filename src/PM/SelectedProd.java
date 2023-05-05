package PM;

public class SelectedProd {

    private PasswordModel selectedProd;

    private static final SelectedProd INSTANCE = new SelectedProd();

    private SelectedProd(){}

    public static SelectedProd getINSTANCE() {
        return INSTANCE;
    }


    public PasswordModel getSelectedProd() {
        return selectedProd;
    }

    public void setSelectedProd(PasswordModel selectedProd) {
        this.selectedProd = selectedProd;
    }

}

