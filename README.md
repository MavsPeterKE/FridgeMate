# FridgeMate
This project creates a simple app that helps user to track expiry dates of products by computing the  days left. User is able to scan product barcodes as well as scan dates. The user adds products to the app which is saved in the database. 


/*******
The project uses mobile vision API For both barcode scanning and date scanning. 

Structure( Tries to emulate MVVP)
packages

ui-contains barcode and ocr activities and dependencies as well

db- contains the apps db creator

adapters - contains recycler/list adapters used

viewModels - contains the logic for my view in regard to data retrieval

utils - contains helper/essential classes used such as the recyclerItem decorator




If Ocr fails to scan date, a normal datepicker is displayed for user to manually feed the date

Product added by the user is saved to sqlite database using the ROOM API



******/
