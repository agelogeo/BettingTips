# Daily Betting Tips

This is an android application developed in order to help users earn some money from betting.
It contains a main navigation drawer for easier navigation through the app and a several async tasks

Drawer List : 
- Today's Tips
- History of Tips
- Bonus Tips
- Statistics
(More social)
- Rate our app
- Suggest it to a friend
- About us.

# Google Play 
You can find our app in the link : https://play.google.com/store/apps/details?id=gr.betting.admin.bettingtips

Please feel free to comment,rate,suggest any ideas about our application.

# Import Data
Data is being imported every morning by our team via google drive. There is a document with 7 sheets :
- Standard_Today
- Standard_History
- Alt_Today
- Alt_History
- Bonus_Today
- Bonus_History
- Statistics


# Sync Data
For data synchronization we used an online service called Google Spreadsheets created by Google.
We export data to JSON format by calling an external google script created by a third person.
Our app implements 7 async tasks, in order to disguise our loading times we used a beautiful splash screen when the user launching the app
Also, we managed to reduced dead times inside the app by collecting all the async tasks on splash screen, this way we succeeded a flowable layout without any loading dialogs inside the app.




