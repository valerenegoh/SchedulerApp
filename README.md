# SchedulerApp
SUTD Term 3 Second Android App Project

## Contributors
UI Design, Firebase Database & prototype: Valerene Goh

Report content: Tracy Yee

## Introduction
In this report, we will summarise our proposal, design and development of a meaningful solution to an open problem using the information systems concepts, software and system components.

Theme: Smart classroom / office and workplace

Problem: Reading and responding to emails (admin task) is extremely time-consuming and unproductive.

Solution: A user-friendly app that facilitates the booking of consultation sessions for students and professors (current market: SUTD) without going through the tedious and time-consuming process of email.

## Distinguishing features:
- Ease of booking: Students can easily book / remove personal consultation slots in designated time periods pre-assigned by professors based on their availability

- Flexibility: Professors can view and resize consultation slots based on content covered to accommodate their schedule and to ensure more accurate reflection of consultation timings, or
approve / reject the slot entirely

- Transparency: Everyone can see who has booked which slot and what is going to be covered during the slot

- Grouping: Students with similar questions / topics are encouraged to group together and join the same consultation slot (to avoid unnecessary repetition of topics)

- Smart: Makes smart suggestions based on inputs and past patterns to facilitate the booking of slots for both students and professors and prevent any clashing / unpopular timings

- Notification alerts: Gives reminders on upcoming events and keeps users informed of any new changes

- Venue booking: Pre-allocated venues proportional to the consultation group size are automatically assigned to the consultations when they are booked

## UI Slices

<img width="160" alt="2" src="https://user-images.githubusercontent.com/23626462/35309077-947d8b7e-00e5-11e8-9791-287d550b0c6e.png">

### Student registration
<img width="161" alt="1" src="https://user-images.githubusercontent.com/23626462/35309076-9448b052-00e5-11e8-8831-c9a8927c6b74.png"><img width="160" alt="7" src="https://user-images.githubusercontent.com/23626462/35309082-95a6abde-00e5-11e8-898e-47c3ce8f9228.png"><img width="159" alt="8" src="https://user-images.githubusercontent.com/23626462/35309083-95dac680-00e5-11e8-87a2-e3c1dee24d30.png">

### Professor registration
<img width="160" alt="6" src="https://user-images.githubusercontent.com/23626462/35309081-9572424a-00e5-11e8-8a8a-fe0f73d84c6d.png"><img width="160" alt="3" src="https://user-images.githubusercontent.com/23626462/35309078-94e1df5c-00e5-11e8-89ba-f422f8f1a76d.png"><img width="160" alt="4" src="https://user-images.githubusercontent.com/23626462/35309079-9511156a-00e5-11e8-9000-38ced481181c.png"><img width="160" alt="5" src="https://user-images.githubusercontent.com/23626462/35309080-953f2cb6-00e5-11e8-9691-e167efe7dfe5.png">

### Booking
<img width="162" alt="13" src="https://user-images.githubusercontent.com/23626462/35309088-96d2198a-00e5-11e8-9e8b-d6296e8d3495.png"><img width="160" alt="14" src="https://user-images.githubusercontent.com/23626462/35309089-970104c0-00e5-11e8-87ec-229c0a6b6e0d.png"><img width="161" alt="15" src="https://user-images.githubusercontent.com/23626462/35309090-9731f968-00e5-11e8-98be-893c458bdddc.png"><img width="162" alt="16" src="https://user-images.githubusercontent.com/23626462/35309091-97639ba8-00e5-11e8-9c4c-7d75a2e2e871.png"><img width="161" alt="17" src="https://user-images.githubusercontent.com/23626462/35309092-97923044-00e5-11e8-803c-5a37a2f1ad26.png"><img width="161" alt="18" src="https://user-images.githubusercontent.com/23626462/35309093-97bfb9f6-00e5-11e8-96f6-f7579fe1ed95.png">

### Main Feed displays all consultations booking pertaining to your selected modules and professors that are within three days of appointment.
<img width="160" alt="9" src="https://user-images.githubusercontent.com/23626462/35313801-58b43076-00fd-11e8-8b2a-1b4a8289ac17.png"><img width="161" alt="29" src="https://user-images.githubusercontent.com/23626462/35309074-93e95256-00e5-11e8-9c92-4151f5f9176d.png">

### Bookings Tab displays your previous, upcoming, favourited and pending bookings.
<img width="160" alt="9" src="https://user-images.githubusercontent.com/23626462/35312823-5f28f270-00f8-11e8-9b3a-4cd220786b2e.png"><img width="160" alt="10" src="https://user-images.githubusercontent.com/23626462/35309085-963dd7a2-00e5-11e8-9254-85075baede99.png"><img width="160" alt="11" src="https://user-images.githubusercontent.com/23626462/35309086-966b9278-00e5-11e8-91df-5d5ebb272d10.png"><img width="161" alt="12" src="https://user-images.githubusercontent.com/23626462/35309087-96a10aac-00e5-11e8-8fb6-4ca797c023ab.png">

### Profile Tab
<img width="161" alt="19" src="https://user-images.githubusercontent.com/23626462/35309094-97f2ef24-00e5-11e8-9b41-2b7081a9dc6b.png"><img width="160" alt="20" src="https://user-images.githubusercontent.com/23626462/35309095-98278888-00e5-11e8-952c-f01d69314c54.png"><img width="160" alt="27" src="https://user-images.githubusercontent.com/23626462/35309072-9387fce0-00e5-11e8-94c5-f2141b370657.png"><img width="161" alt="28" src="https://user-images.githubusercontent.com/23626462/35309073-93b5db10-00e5-11e8-92ae-d8c54cdd7a49.png"><img width="162" alt="21" src="https://user-images.githubusercontent.com/23626462/35309096-985ba104-00e5-11e8-9a6a-20a06a17013a.png"><img width="160" alt="26" src="https://user-images.githubusercontent.com/23626462/35309071-93594242-00e5-11e8-902b-8fae787e901b.png"><img width="160" alt="22" src="https://user-images.githubusercontent.com/23626462/35309098-988e8376-00e5-11e8-944f-ab60f31c725d.png"><img width="161" alt="23" src="https://user-images.githubusercontent.com/23626462/35309099-98c1d5e6-00e5-11e8-8592-e6641a4016b8.png"><img width="160" alt="24" src="https://user-images.githubusercontent.com/23626462/35309100-98f24e10-00e5-11e8-8c92-fd8e1b17ef82.png"><img width="160" alt="25" src="https://user-images.githubusercontent.com/23626462/35309101-9921f25a-00e5-11e8-9b19-b7c4a250a939.png">

## System Design and Implementation:

### Object-Oriented, Hierarchal and Modular Design
• The system is partitioned into layers and each layer is decomposed to form the subsystems.

<img width="169" alt="capture" src="https://user-images.githubusercontent.com/23626462/35309075-941a9672-00e5-11e8-9fc8-c65160d794e9.PNG">

• Sublayers (e.g. the Consultation Application Layer and Alternative Applications Layer) implement
the methods in the base layer (Appointment Scheduler Layer). This allows our scheduler app to have varied customised applications across different organisations while still retaining its key defining features.

• Within each sublayer, the individual components are of less complexity (only the customisable features are implemented here), which allows us to easily adapt features based on the
individual components.

• Subsystems can be easily replaced or modified without affecting other subsystems.

### Real-Time Database (integrating Firebase with our app)
• The dynamic content and user database are all stored and retrieved from firebase whenever needed e.g. user authentication and syncing of real-time data between users

• The Firebase database stores information in NoSQL (similar to a huge JSON file) hosted in the Google Cloud.

  - Real-Time: When information is altered, it is instantly synchronised with the Cloud within milliseconds (no batching and grouping of data before being sent)

    o When users update information in the app, e.g. creating a new user account or adding a new booking, we push nodes into our database with a unique key as an ID and the changes are reflected in the database structure.
  
  - Automatic synchronization and conflict solving: devices can subscribe to sections of the database and automatically retrieve the required data when it is being altered

    o The app also gets notified whenever data has been changed in the database by subscribing to any value that is changed in the specified node, e.g. an account created using the app is now activated and can be used to sign into our app.
    
Organistion of data in Firebase:

<img width="140" alt="firebase" src="https://user-images.githubusercontent.com/23626462/35307682-cc5af11e-00de-11e8-8ad0-d6da30d7ddcf.PNG">

## Further development Plan:
• We plan to launch the app in SUTD for small-scale market testing to determine the suitability of our app and adjust it based on the feedback from users. If it is successful, we will look to expand to other organisations, e.g. other educational institutions, offices, etc.
  
  - The modularity of our app allows us to implement the same base features, but with different customisations for each organisation, thereby giving users the feeling of personalisation and that the app understands what they need and want in a scheduler.
  
  - Some examples of different customisations for different organisations are:
    
    o Scheduling meetings instead of consultation slots for offices
    
    o Venue bookings for venues unique to the organisation

• Incorporate machine learning to further improve and enhance the “smart” aspect of our scheduler. This will allow our scheduler app to make smart suggestions and give advice to users when scheduling their appointments, thus drastically reducing the amount of thinking and effort on the part of the user in scheduling. We aim to almost completely reduce the time spent on such inefficient admin tasks. Some examples of incorporated smart schedulers are:
  
  - Automated scheduling based on text messages (text scheduler)
    
    o This version of the scheduler is able to “read” text messages and schedule events and meetups accordingly based on your text messages.
    
    o However, this brings up the issue of privacy, which is an important aspect that we will have to research on and implement to ensure the confidentiality of our users.
  
  - Automated scheduling for life goals (life scheduler)
   
    o Helps people with actually acting on their life goals by removing the initial procrastination phase of finding time.
  
    o It gives suggestions on how to improve your life (life goals) based on your current lifestyle and automatically schedules it in for you.
 
 - Automated synching of friends’ schedules to arrange optimal meetup times (meetup scheduler)

▪ Automatically finds the nearest upcoming common meeting time for the entire group and a venue / activity that is convenient and interesting for everyone.

▪ If there is no common meeting time in the short term, it can suggest possible schedule shifts based on task priority in the schedules of some of the group members.

• Can also possibly be adapted for part-time job scheduling (e.g. working schedule for part-timers)

## Conclusion:
In conclusion, over the course of this project, we managed to develop a working app to address the problem of time-consuming administrative work involving scheduling appointments. Our system development made use of Java extensively, with good software practice and effective programming, including but not limited to object-oriented design, inheritance, encapsulation, generic programming and the trying out of various design patterns (such as the Subscriber Design Pattern and the Thread Pool Design Pattern). With the introduction of our app, there will be less time wastage on administrative tasks, freeing up time for more productive work. Furthermore, as administrative tasks are integral in every aspect of our lives, our solution has the potential to have widespread applications, from the global scale to the industrial scale to the individual scale (e.g. personal scheduling, day-to-day use).

## References: 
https://medium.com/google-developer-experts/using-firebase-as-a-real-time-system-d360265aa678
