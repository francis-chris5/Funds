@ECHO OFF

@ECHO OFF


echo+
IF NOT EXIST javafx\ (
	echo Need to download some dependencies first...
	echo+
	curl https://download2.gluonhq.com/openjfx/11.0.2/openjfx-11.0.2_windows-x64_bin-sdk.zip --output javafx.zip


	Powershell.exe Expand-Archive javafx.zip
	echo+
)

echo Opening Funds Application...
echo+

REM powershell -window hidden -command ''

java -cp %cd%\bin --module-path "%cd%\javafx\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml --add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED --add-exports javafx.base/com.sun.javafx.event=ALL-UNNAMED Funds.FundMain

