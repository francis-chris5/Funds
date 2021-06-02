#! /bin/zsh


echo
if [ ! -d javafx-sdk-11.0.2 ] 
then
	echo Need to download some dependencies first
	echo
	curl https://download2.gluonhq.com/openjfx/11.0.2/openjfx-11.0.2_osx-x64_bin-sdk.zip --output javafx.zip
	unzip javafx.zip > temp.txt
	rm javafx.zip > temp.txt
	rm temp.txt
	echo
fi

echo Opening Routines Application...
echo
current=$(pwd)
java -cp $current/bin --module-path $current/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml,javafx.web Funds.FundMain
