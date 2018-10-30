REM https://github.com/korlibs/klock/blob/2614171f6981b5905768f4c225114b0a2ebf3a79/travis_win.bat

RD /s /q "c:\Program Files\IIS"
RD /s /q "c:\Program Files\Java"
RD /s /q "c:\Program Files\Microsoft"
RD /s /q "c:\Program Files\Microsoft Visual Studio"
RD /s /q "c:\Program Files\Microsoft Visual Studio 14.0"
RD /s /q "c:\Program Files\cmake"
RD /s /q "c:\Program Files\Microsoft SDKs"
RD /s /q "c:\Program Files (x86)\IIS"
RD /s /q "c:\Program Files (x86)\Java"
RD /s /q "c:\Program Files (x86)\Microsoft"
RD /s /q "c:\Program Files (x86)\Microsoft Visual Studio"
RD /s /q "c:\Program Files (x86)\Microsoft Visual Studio 14.0"
RD /s /q "c:\Program Files (x86)\cmake"
RD /s /q "c:\Program Files (x86)\Microsoft SDKs"

choco install jdk8 -y -params "installdir=c:\\java8"

del c:\java8\src.zip
del c:\java8\javafx-src.zip
