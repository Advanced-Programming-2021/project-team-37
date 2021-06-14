package controller;

public class ImportOrExportPageController extends Controller
{
    private static ImportOrExportPageController instance;

    
    private ImportOrExportPageController()
    {
        
    }		
    
    private ImportOrExportPageController getInstance() 		
    {
        if (instance == null)
            instance = new ImportOrExportPageController();
        return instance;
    }

    @Override
    public void exit() {

    }

    @Override
    public void showCurrentMenu() {

    }
}
