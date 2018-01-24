# SchedulerApp
SUTD Term 3 Second Android App Project

## Contributors
UI Design & Firebase Database: Valerene Goh

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
![4](https://user-images.githubusercontent.com/23626462/35307492-02d82014-00de-11e8-9737-bcba7da66b06.jpeg)
![5](https://user-images.githubusercontent.com/23626462/35307493-030edae6-00de-11e8-890c-01d8cb1390c7.jpeg)
![6](https://user-images.githubusercontent.com/23626462/35307494-03450512-00de-11e8-85cb-78d76e1639db.jpeg)
![7](https://user-images.githubusercontent.com/23626462/35307495-037a2bf2-00de-11e8-86e9-163275c12b74.jpeg)
![8](https://user-images.githubusercontent.com/23626462/35307496-03b38032-00de-11e8-9d6f-b9d66cf49e7c.jpeg)
![9](https://user-images.githubusercontent.com/23626462/35307499-03f02780-00de-11e8-8598-a914888b9c8b.jpeg)
![10](https://user-images.githubusercontent.com/23626462/35307503-045da36e-00de-11e8-818e-cea71e674efa.jpeg)
![11](https://user-images.githubusercontent.com/23626462/35307505-049301c6-00de-11e8-9408-37a6bccf4903.jpeg)
![12](https://user-images.githubusercontent.com/23626462/35307506-04c958d4-00de-11e8-8e6e-2849f1f60ef1.jpeg)
![13](https://user-images.githubusercontent.com/23626462/35307507-04fe355e-00de-11e8-9f84-2fd83420150b.jpeg)
![14](https://user-images.githubusercontent.com/23626462/35307509-05436c46-00de-11e8-899b-30a86054a067.jpeg)
![15](https://user-images.githubusercontent.com/23626462/35307510-0578268e-00de-11e8-969d-d132e30f2267.jpeg)
![16](https://user-images.githubusercontent.com/23626462/35307511-05aefc90-00de-11e8-9d51-9c4ab661d447.jpeg)
![17](https://user-images.githubusercontent.com/23626462/35307513-05e7ed2a-00de-11e8-9f04-4115efe32175.jpeg)
![18](https://user-images.githubusercontent.com/23626462/35307514-061e60c6-00de-11e8-9d36-ce17b37d121a.jpeg)
![19](https://user-images.githubusercontent.com/23626462/35307515-0653df44-00de-11e8-8221-91bcbb0cfdf5.jpeg)
![20](https://user-images.githubusercontent.com/23626462/35307516-0691b206-00de-11e8-9df4-80cc1c99cb80.jpeg)
![21](https://user-images.githubusercontent.com/23626462/35307517-06c299d4-00de-11e8-9f5d-24337e6ceae3.jpeg)
![22](https://user-images.githubusercontent.com/23626462/35307519-06f58286-00de-11e8-8c77-a26e571f1f45.jpeg)
![23](https://user-images.githubusercontent.com/23626462/35307520-0727c99e-00de-11e8-9748-cf7a52b2920b.jpeg)
![24](https://user-images.githubusercontent.com/23626462/35307521-075eb9f4-00de-11e8-8261-dd1f526b6840.jpeg)
![25](https://user-images.githubusercontent.com/23626462/35307522-07916016-00de-11e8-9f16-5a6af7e5c953.jpeg)
![26](https://user-images.githubusercontent.com/23626462/35307523-07c0bcbc-00de-11e8-912a-461d43de9c54.jpeg)
![27](https://user-images.githubusercontent.com/23626462/35307524-07f31806-00de-11e8-8ea1-be74199d22f0.jpeg)
![28](https://user-images.githubusercontent.com/23626462/35307525-08236088-00de-11e8-8e80-ae6f69830a69.jpeg)
![29](https://user-images.githubusercontent.com/23626462/35307526-08567252-00de-11e8-998a-efa845c0818e.jpeg)
![1](https://user-images.githubusercontent.com/23626462/35307527-088a86be-00de-11e8-8fef-c6c098a88d54.jpeg)
![2](https://user-images.githubusercontent.com/23626462/35307528-08c2aa12-00de-11e8-824c-93c18a7eb84b.jpeg)
![3](https://user-images.githubusercontent.com/23626462/35307529-08f7c562-00de-11e8-85aa-046710ddc088.jpeg)

## System Design and Implementation:

### Object-Oriented, Hierarchal and Modular Design
• The system is partitioned into layers and each layer is decomposed to form the subsystems.

<img width="144" alt="capture" src="https://user-images.githubusercontent.com/23626462/35307681-cc24cf58-00de-11e8-897d-fc36988f5108.PNG">

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
