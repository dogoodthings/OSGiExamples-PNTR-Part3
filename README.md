# SAP ECTR OSGi Example - PartNameTokenReplacementService 

How to enhance file naming  

Setup project in eclipse:

1. File - Import - Maven - Checkout Maven Projects from SCM
2. SCM URL: git, https://github.com/dogoodthings/OSGiExamples-PNTR-Part3.git
3. wait a while :P
4. open pom.xml and check the property ectr.installation.directory
5. locate your ECTR installation ( in most cases it is C:\Program Files (x86)\SAP\ECTR )
6. edit pom.xml and set the property ectr.installation.directory to this path
7. (do any meaningful changes to the source code)
8. right click on the project - run as - maven - goals: clean package - hit "run"
9. take the generated jar from target folder: OSGi-Examples-PNTR-Part3-1.0.0.jar
10. goto ECTR installation, create a folder(s) OSGi-Examples\basis\plugins inside <ectr_inst_dir>\addons  (mkdir "C:\Program Files (x86)\SAP\ECTR\addons\OSGi-Examples\basis\plugins")
11. put OSGi-Examples-PNTR-Part3-1.0.0.jar inside OSGi-Examples\basis\plugins
12. change some ECTR DType for NX, add the token $(TEMPLATE_FILENAME) somewhere in filename_template_base:
  <document_create
   filename_template_base="$(DOCNUMBER)$(DOCTYPE)$(DOCPART)$(TEMPLATE_FILENAME)_"
13. start ECTR, create or open a native part in NX and create document for it using DType you just changed.
14. the filename of the created document should be extended with original filename like specified in ilename_template_base
15. done.
16. note - you need at least ECTR version 5.1.7.0 to get the example code to work
