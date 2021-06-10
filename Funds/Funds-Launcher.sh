#! /bin/bash

echo
if [ ! -d javafx-sdk-11.0.2 ]
then
	echo Need to download some dependencies first...
	echo
	wget https://download2.gluonhq.com/openjfx/11.0.2/openjfx-11.0.2_linux-x64_bin-sdk.zip > temp.txt
	unzip openjfx-11.0.2_linux-x64_bin-sdk.zip > temp.txt
	rm openjfx-11.0.2_linux-x64_bin-sdk.zip > temp.txt
	rm temp.txt
	echo
fi


echo Opening Routines Application...
echo
current=$(pwd)
java -cp "$current/bin" --module-path "$current/javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml,javafx.web Funds.FundMain
