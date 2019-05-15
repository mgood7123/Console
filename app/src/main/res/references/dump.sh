adb shell uiautomator dump && adb pull /sdcard/window_dump.xml && xmllint --format window_dump.xml > ../layout/window_dump_formatted.xml ; rm *.xml
