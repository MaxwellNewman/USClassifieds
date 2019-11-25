# USClassifieds

### Import instructions
To run the USClassifieds, download the project as a zip and extract the files.
To import the project into Android Studio, double tap shift and type `Import Project` then hit enter once you see "Import Project...".
Then select the directory where you extracted the files to.  For reference, the import settings are
located in the `USClassifieds/config/` directory, however, these settings will likely not be needed).

### Emulator setup instructions
We ran the app on the Pixel 2 emulator, with a 1080 x 1920 resolution.  For our operating system, we 
used Q (Android 10.0) with Google Play APIs enabled.  For best results, we recommend you use these settings.

### Run instructions
To run the app, press the green play button in the top of Android Studio.  

### Testing instructions

To run the black box tests, on the left hand side of android studio, select the
`1: Project` tab.  Go to app>src>androidTest and right click on `java` and select "Run tests in Java".

Similarly, to run the white box tests go to app>src>test and right click on `java` and select "Run tests in Java".

#### Code coverage

In the drop down menu to the left of the emulator, select  `Edit Configurations`.
Click the `+` sign in the top-left, and select 'Android Junit'.  Change the name to 'Code Coverage'.
Change 'Test Kind' to 'All in directory'.  Make sure that 'Directory' is the path to app/src/test/com/cs310/usclassifieds.
For VM Options, change it to "-ea -noverify".  Also, make sure that 'Use classpath or module' is selected on 'app'.

Next, switch to the 'Code Coverage' tab.  Under Packages and Classes to include, add the com.cs.310.usclassifieds.model.datamodel.*, com.cs.310.usclassifieds.model.manager.* packages.

Under the classes to exclude com.cs.310.usclassifieds.model.manager.

If you have difficulties running the classes with coverage, you can right click on the green arrow next to the class, and then choose to run with coverage.
When running SearchManager tests you have to have the "-ea -noverify".