create table supported_currency
(
    Code char(3), -- ISO 4217
    primary key (Code)
);

create table country_display_currency
(
    Country  char(35),
    Currency char(3),
    primary key (Country),
    foreign key (Currency) references supported_currency (Code)
        on delete set null
);

create table traveller
(
    Username  char(15),
    Name      char(35),
    Country   char(35),
    Province  char(35),
    City      char(35),
    Gender    char(5),
    Birthdate date,
    primary key (Username),
    foreign key (Country) references country_display_currency
        on delete set null
);

create table business
(
    Username char(15),
    Name     char(35),
    Country  char(35),
    Province char(35),
    City     char(35),
    primary key (Username),
    foreign key (Country) references country_display_currency
        on delete set null
);

create table traveller_bucket_lists
(
    Username char(15),
    Title    char(35),
    primary key (Username, Title),
    foreign key (Username) references traveller
        on delete cascade
);

create table interest
(
    Name        char(35),
    Description varchar(150 char),
    primary key (Name)
);

create table bucket_list_interests
(
    Username          char(15),
    Bucket_List_Title char(35),
    Interest_Name     char(35),
    primary key (Username, Bucket_List_Title, Interest_Name),
    foreign key (Username, Bucket_List_Title) references traveller_bucket_lists (Username, Title)
        on delete cascade,
    foreign key (Interest_Name) references interest (Name)
        on delete cascade
);

create table traveller_group
(
    Group_ID       integer,
    Title          char(35),
    Description    varchar(150 char),
    Owner_Username char(15),
    primary key (Group_ID),
    foreign key (Owner_Username) references traveller (Username)
        on delete cascade
);

create table business_group
(
    Group_ID       integer,
    Title          char(35),
    Description    varchar(150 char),
    Owner_Username char(15),
    primary key (Group_ID),
    foreign key (Owner_Username) references business (Username)
        on delete cascade
);

create table trav_group_member_travellers
(
    Group_ID integer,
    Username char(15),
    primary key (Group_ID, Username),
    foreign key (Group_ID) references traveller_group
        on delete cascade,
    foreign key (Username) references traveller
    on delete cascade
);

create table bus_group_member_travellers
(
    Group_ID integer,
    Username char(15),
    primary key (Group_ID, Username),
    foreign key (Group_ID) references business_group
        on delete cascade,
    foreign key (Username) references traveller
        on delete cascade
);

create table trav_group_member_businesses
(
    Group_ID integer,
    Username char(15),
    primary key (Group_ID, Username),
    foreign key (Group_ID) references traveller_group
        on delete cascade,
    foreign key (Username) references business
    on delete cascade
);

create table bus_group_member_businesses
(
    Group_ID integer,
    Username char(15),
    primary key (Group_ID, Username),
    foreign key (Group_ID) references business_group
        on delete cascade,
    foreign key (Username) references business
        on delete cascade
);

create table trav_group_interests
(
    Group_ID      integer,
    Interest_Name char(35),
    primary key (Group_ID, Interest_Name),
    foreign key (Group_ID) references traveller_group
        on delete cascade,
    foreign key (Interest_Name) references interest (Name)
        on delete cascade
);

create table bus_group_interests
(
    Group_ID      integer,
    Interest_Name char(35),
    primary key (Group_ID, Interest_Name),
    foreign key (Group_ID) references business_group
        on delete cascade,
    foreign key (Interest_Name) references interest (Name)
        on delete cascade
);

create table trav_grp_trip
(
    Trip_ID          integer,
    Group_ID         integer not null,
    Meeting_Location varchar(150 char),
    Title            char(35),
    Group_Limit      integer,
    Trip_Cost        number,
    Currency         char(3) not null,
    Description      varchar(150 char),
    Start_Date       date,
    End_Date         date,
    primary key (Trip_ID),
    foreign key (Group_ID) references traveller_group
        on delete cascade,
    foreign key (Currency) references supported_currency (Code)
);

create table bus_grp_trip
(
    Trip_ID          integer,
    Group_ID         integer not null,
    Meeting_Location varchar(150 char),
    Title            char(35),
    Group_Limit      integer,
    Trip_Cost        number,
    Currency         char(3) not null,
    Description      varchar(150 char),
    Start_Date       date,
    End_Date         date,
    primary key (Trip_ID),
    foreign key (Group_ID) references business_group
        on delete cascade,
    foreign key (Currency) references supported_currency (Code)
);

create table trav_grp_trp_activity
(
    Activity_ID      integer,
    Trip_ID          integer not null,
    Meeting_Location varchar(150 char),
    Title            char(35),
    Activity_Cost    number,
    Description      varchar(150 char),
    Start_Date       date,
    End_Date         date,
    primary key (Activity_ID),
    foreign key (Trip_ID) references trav_grp_trip
        on delete cascade
);

create table bus_grp_trp_activity
(
    Activity_ID      integer,
    Trip_ID          integer not null,
    Meeting_Location varchar(150 char),
    Title            char(35),
    Activity_Cost    number,
    Description      varchar(150 char),
    Start_Date       date,
    End_Date         date,
    primary key (Activity_ID),
    foreign key (Trip_ID) references bus_grp_trip
        on delete cascade
);

create table trav_group_sponsored_deals
(
    Promo_Code        char(100),
    Business_Username char(15)        not null,
    Group_ID          integer not null,
    Condition         varchar(150 char),
    Deal              varchar(150 char),
    primary key (Promo_Code),
    foreign key (Business_Username) references business (Username)
        on delete cascade,
    foreign key (Group_ID) references traveller_group
        on delete cascade
);

create table bus_grp_sponsored_deals
(
    Promo_Code        char(100),
    Business_Username char(15)        not null,
    Group_ID          integer not null,
    Condition         varchar(150 char),
    Deal              varchar(150 char),
    primary key (Promo_Code),
    foreign key (Business_Username) references business (Username)
        on delete cascade,
    foreign key (Group_ID) references business_group
        on delete cascade
);

create table trav_grp_trip_interests
(
    Trip_ID       integer,
    Interest_Name char(35),
    primary key (Trip_ID, Interest_Name),
    foreign key (Trip_ID) references trav_grp_trip
        on delete cascade,
    foreign key (Interest_Name) references interest (Name)
        on delete cascade
);

create table bus_grp_trip_interests
(
    Trip_ID       integer,
    Interest_Name char(35),
    primary key (Trip_ID, Interest_Name),
    foreign key (Trip_ID) references bus_grp_trip
        on delete cascade,
    foreign key (Interest_Name) references interest (Name)
        on delete cascade
);

create table trav_grp_trp_traveller_forum_posts
(
    Post_ID         integer,
    Title           char(35),
    Body            varchar(150 char),
    Time_Posted     timestamp,
    Author_Username char(15),
    Trip_ID         integer not null,
    primary key (Post_ID),
    foreign key (Author_Username) references traveller (Username)
        on delete set null,
    foreign key (Trip_ID) references trav_grp_trip
        on delete cascade
);

create table bus_grp_trp_traveller_forum_posts
(
    Post_ID         integer,
    Title           char(35),
    Body            varchar(150 char),
    Time_Posted     timestamp,
    Author_Username char(15),
    Trip_ID         integer not null,
    primary key (Post_ID),
    foreign key (Author_Username) references traveller (Username)
        on delete set null,
    foreign key (Trip_ID) references bus_grp_trip
        on delete cascade
);

create table trav_grp_trp_business_forum_posts
(
    Post_ID         integer,
    Title           char(35),
    Body            varchar(150 char),
    Time_Posted     timestamp,
    Author_Username char(15),
    Trip_ID         integer not null,
    primary key (Post_ID),
    foreign key (Author_Username) references business (Username)
        on delete set null,
    foreign key (Trip_ID) references trav_grp_trip
        on delete cascade
);

create table bus_grp_trp_business_forum_posts
(
    Post_ID         integer,
    Title           char(35),
    Body            varchar(150 char),
    Time_Posted     timestamp,
    Author_Username char(15),
    Trip_ID         integer not null,
    primary key (Post_ID),
    foreign key (Author_Username) references business (Username)
        on delete set null,
    foreign key (Trip_ID) references bus_grp_trip
        on delete cascade
);

create table trav_grp_trp_trav_post_traveller_comments
(
    Post_ID         integer,
    Comment_ID      integer,
    Body            varchar(150 char),
    Time_Commented  timestamp,
    Author_Username char(15),
    primary key (Post_ID, Comment_ID),
    foreign key (Post_ID) references trav_grp_trp_traveller_forum_posts
        on delete cascade,
    foreign key (Author_Username) references traveller (Username)
        on delete set null
);

create table bus_grp_trp_trav_post_traveller_comments
(
    Post_ID         integer,
    Comment_ID      integer,
    Body            varchar(150 char),
    Time_Commented  timestamp,
    Author_Username char(15),
    primary key (Post_ID, Comment_ID),
    foreign key (Post_ID) references bus_grp_trp_traveller_forum_posts
        on delete cascade,
    foreign key (Author_Username) references traveller (Username)
        on delete set null
);

create table trav_grp_trp_trav_post_business_comments
(
    Post_ID         integer,
    Comment_ID      integer,
    Body            varchar(150 char),
    Time_Commented  timestamp,
    Author_Username char(15),
    primary key (Post_ID, Comment_ID),
    foreign key (Post_ID) references trav_grp_trp_traveller_forum_posts
        on delete cascade,
    foreign key (Author_Username) references business (Username)
        on delete set null
);

create table bus_grp_trp_trav_post_business_comments
(
    Post_ID         integer,
    Comment_ID      integer,
    Body            varchar(150 char),
    Time_Commented  timestamp,
    Author_Username char(15),
    primary key (Post_ID, Comment_ID),
    foreign key (Post_ID) references bus_grp_trp_traveller_forum_posts
        on delete cascade,
    foreign key (Author_Username) references business (Username)
        on delete set null
);

create table trav_grp_trp_bus_post_traveller_comments
(
    Post_ID         integer,
    Comment_ID      integer,
    Body            varchar(150 char),
    Time_Commented  timestamp,
    Author_Username char(15),
    primary key (Post_ID, Comment_ID),
    foreign key (Post_ID) references trav_grp_trp_business_forum_posts
        on delete cascade,
    foreign key (Author_Username) references traveller (Username)
        on delete set null
);

create table bus_grp_trp_bus_post_traveller_comments
(
    Post_ID         integer,
    Comment_ID      integer,
    Body            varchar(150 char),
    Time_Commented  timestamp,
    Author_Username char(15),
    primary key (Post_ID, Comment_ID),
    foreign key (Post_ID) references bus_grp_trp_business_forum_posts
        on delete cascade,
    foreign key (Author_Username) references traveller (Username)
        on delete set null
);

create table trav_grp_trp_bus_post_business_comments
(
    Post_ID         integer,
    Comment_ID      integer,
    Body            varchar(150 char),
    Time_Commented  timestamp,
    Author_Username char(15),
    primary key (Post_ID, Comment_ID),
    foreign key (Post_ID) references trav_grp_trp_business_forum_posts
        on delete cascade,
    foreign key (Author_Username) references business (Username)
        on delete set null
);

create table bus_grp_trp_bus_post_business_comments
(
    Post_ID         integer,
    Comment_ID      integer,
    Body            varchar(150 char),
    Time_Commented  timestamp,
    Author_Username char(15),
    primary key (Post_ID, Comment_ID),
    foreign key (Post_ID) references bus_grp_trp_business_forum_posts
        on delete cascade,
    foreign key (Author_Username) references business (Username)
        on delete set null
);

insert into SUPPORTED_CURRENCY (CODE)
values ('CAD');
insert into SUPPORTED_CURRENCY (CODE)
values ('USD');
insert into SUPPORTED_CURRENCY (CODE)
values ('CNY');
insert into SUPPORTED_CURRENCY (CODE)
values ('INR');
insert into SUPPORTED_CURRENCY (CODE)
values ('GBP');
insert into SUPPORTED_CURRENCY (CODE)
values ('EUR');
insert into SUPPORTED_CURRENCY (CODE)
values ('TWD');

insert into COUNTRY_DISPLAY_CURRENCY (COUNTRY, CURRENCY)
values ('Canada', 'CAD');
insert into COUNTRY_DISPLAY_CURRENCY (COUNTRY, CURRENCY)
values ('USA', 'USD');
insert into COUNTRY_DISPLAY_CURRENCY (COUNTRY, CURRENCY)
values ('China', 'CNY');
insert into COUNTRY_DISPLAY_CURRENCY (COUNTRY, CURRENCY)
values ('India', 'INR');
insert into COUNTRY_DISPLAY_CURRENCY (COUNTRY, CURRENCY)
values ('U.K.', 'GBP');
insert into COUNTRY_DISPLAY_CURRENCY (COUNTRY, CURRENCY)
values ('France', 'EUR');
insert into COUNTRY_DISPLAY_CURRENCY (COUNTRY, CURRENCY)
values ('Taiwan', 'TWD');

insert into TRAVELLER (USERNAME, NAME, COUNTRY, PROVINCE, CITY, GENDER, BIRTHDATE)
values ('letorysi', 'Jane Doe', 'Canada', 'BC', 'Vancouver', 'F', '1989-06-06');
insert into TRAVELLER (USERNAME, NAME, COUNTRY, PROVINCE, CITY, GENDER, BIRTHDATE)
values ('oberberi', 'Larry Frost', 'USA', 'California', 'Temecula', 'M', '1995-05-06');
insert into TRAVELLER (USERNAME, NAME, COUNTRY, PROVINCE, CITY, GENDER, BIRTHDATE)
values ('weaselyy7', 'Ron Weasely', 'U.K.', 'Devon', 'Plymouth', 'Other', '1965-04-12');
insert into TRAVELLER (USERNAME, NAME, COUNTRY, PROVINCE, CITY, GENDER, BIRTHDATE)
values ('ucivermi32', 'Cho Chang', 'China', 'Guangdong', 'Guangzhou', 'F', '1980-12-01');
insert into TRAVELLER (USERNAME, NAME, COUNTRY, PROVINCE, CITY, GENDER, BIRTHDATE)
values ('erdistro', 'Perry Bull', 'Canada', 'Ontario', 'London', 'M', '1997-03-25');
insert into TRAVELLER (USERNAME, NAME, COUNTRY, PROVINCE, CITY, GENDER, BIRTHDATE)
values ('meme009', '( ͡° ͜ʖ ͡°)', 'USA', 'Washington', 'Seattle', 'Other', '1999-12-31');

insert into BUSINESS (USERNAME, NAME, COUNTRY, PROVINCE, CITY)
values ('lululemon_admin', 'Lululemon', 'Canada', 'BC', 'Vancouver');
insert into BUSINESS (USERNAME, NAME, COUNTRY, PROVINCE, CITY)
values ('gatosgatods', 'Netflix', 'USA', 'California', 'Los Gatos');
insert into BUSINESS (USERNAME, NAME, COUNTRY, PROVINCE, CITY)
values ('skyyscanner08', 'Skyscanner', 'U.K.', 'Edinburgh', 'Edinburgh');
insert into BUSINESS (USERNAME, NAME, COUNTRY, PROVINCE, CITY)
values ('love0totsravel', 'Tripadvisor', 'USA', 'Massachusetts', 'Needham');
insert into BUSINESS (USERNAME, NAME, COUNTRY, PROVINCE, CITY)
values ('coffeeee123', 'Grounds for Coffee', 'Canada', 'BC', 'Vancouver');

insert into TRAVELLER_BUCKET_LISTS (USERNAME, TITLE)
values ('letorysi', 'Before I die');
insert into TRAVELLER_BUCKET_LISTS (USERNAME, TITLE)
values ('oberberi', 'Things to do in Europe');
insert into TRAVELLER_BUCKET_LISTS (USERNAME, TITLE)
values ('weaselyy7', 'Todo in 2020');
insert into TRAVELLER_BUCKET_LISTS (USERNAME, TITLE)
values ('ucivermi32', 'Bucket List Ideas for Travel');
insert into TRAVELLER_BUCKET_LISTS (USERNAME, TITLE)
values ('erdistro', 'Best things to do in Asia');

insert into INTEREST (NAME, DESCRIPTION)
values ('Backpacking', 'Low-cost, independent travel');
insert into INTEREST (NAME, DESCRIPTION)
values ('Make Friends', 'Get social with the locals');
insert into INTEREST (NAME, DESCRIPTION)
values ('Sightseeing', 'Adventurous sightseeing');
insert into INTEREST (NAME, DESCRIPTION)
values ('Museums & Art Galleries', 'Visiting hottest art scene');
insert into INTEREST (NAME, DESCRIPTION)
values ('Outdoor Activity', 'Scuba Diving, Hiking, and more');
insert into INTEREST (NAME, DESCRIPTION)
values ('Restaurants', 'The best local fare');
insert into INTEREST (NAME, DESCRIPTION)
values ('Wellness', 'Mindfulness, meditation, and yoga');
insert into INTEREST (NAME, DESCRIPTION)
values ('Chinese Food', 'Experience all Chinese cuisine has to offer, from Hong Kong milk tea to spicy Sichuan food');
insert into INTEREST (NAME, DESCRIPTION)
values ('Bar Hopping', 'Visit as many bars in one night as you can! Are you ready?');

insert into BUCKET_LIST_INTERESTS (USERNAME, BUCKET_LIST_TITLE, INTEREST_NAME)
values ('letorysi', 'Before I die', 'Backpacking');
insert into BUCKET_LIST_INTERESTS (USERNAME, BUCKET_LIST_TITLE, INTEREST_NAME)
values ('oberberi', 'Things to do in Europe', 'Sightseeing');
insert into BUCKET_LIST_INTERESTS (USERNAME, BUCKET_LIST_TITLE, INTEREST_NAME)
values ('oberberi', 'Things to do in Europe', 'Restaurants');
insert into BUCKET_LIST_INTERESTS (USERNAME, BUCKET_LIST_TITLE, INTEREST_NAME)
values ('weaselyy7', 'Todo in 2020', 'Museums & Art Galleries');
insert into BUCKET_LIST_INTERESTS (USERNAME, BUCKET_LIST_TITLE, INTEREST_NAME)
values ('ucivermi32', 'Bucket List Ideas for Travel', 'Outdoor Activity');
insert into BUCKET_LIST_INTERESTS (USERNAME, BUCKET_LIST_TITLE, INTEREST_NAME)
values ('erdistro', 'Best things to do in Asia', 'Restaurants');

insert into TRAVELLER_GROUP (GROUP_ID, TITLE, DESCRIPTION, OWNER_USERNAME)
values (84737, 'Vancouver Foodie', 'We live to eat', 'letorysi');
insert into TRAVELLER_GROUP (GROUP_ID, TITLE, DESCRIPTION, OWNER_USERNAME)
values (85678, 'Caminos', 'Backpackers', 'letorysi');
insert into TRAVELLER_GROUP (GROUP_ID, TITLE, DESCRIPTION, OWNER_USERNAME)
values (00001, 'Art Near Me', 'People who love European art', 'weaselyy7');
insert into TRAVELLER_GROUP (GROUP_ID, TITLE, DESCRIPTION, OWNER_USERNAME)
values (26264, 'Foodies On Board', 'Asian cuisine lovers', 'erdistro');
insert into TRAVELLER_GROUP (GROUP_ID, TITLE, DESCRIPTION, OWNER_USERNAME)
values (12345, 'Developers who need friends', 'Developers who need friends', 'meme009');

insert into BUSINESS_GROUP (GROUP_ID, TITLE, DESCRIPTION, OWNER_USERNAME)
values (11111, 'City', 'City lovers travelling around the world', 'skyyscanner08');
insert into BUSINESS_GROUP (GROUP_ID, TITLE, DESCRIPTION, OWNER_USERNAME)
values (22222, 'Vancouver Coffee Tours', 'We love local coffee shops', 'coffeeee123');
insert into BUSINESS_GROUP (GROUP_ID, TITLE, DESCRIPTION, OWNER_USERNAME)
values (33333, 'Backpacking Canada', 'We travel across Canada', 'love0totsravel');
insert into BUSINESS_GROUP (GROUP_ID, TITLE, DESCRIPTION, OWNER_USERNAME)
values (44444, 'Vancouverite yoga', 'Practicing yoga at Vancouver''s iconic spots', 'lululemon_admin');
insert into BUSINESS_GROUP (GROUP_ID, TITLE, DESCRIPTION, OWNER_USERNAME)
values (55555, 'Netflix travellers', 'Netflix fans who like to go to film locations', 'gatosgatods');

insert into TRAV_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (84737, 'meme009');
insert into TRAV_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (85678, 'meme009');
insert into TRAV_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (85678, 'oberberi');
insert into TRAV_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (84737, 'erdistro');
insert into TRAV_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (84737, 'oberberi');
insert into TRAV_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (00001, 'weaselyy7');
insert into TRAV_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (00001, 'ucivermi32');
insert into TRAV_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (26264, 'ucivermi32');
insert into TRAV_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (26264, 'weaselyy7');

insert into TRAV_GROUP_MEMBER_BUSINESSES (GROUP_ID, USERNAME)
values (84737, 'love0totsravel');
insert into TRAV_GROUP_MEMBER_BUSINESSES (GROUP_ID, USERNAME)
values (84737, 'coffeeee123');
insert into TRAV_GROUP_MEMBER_BUSINESSES (GROUP_ID, USERNAME)
values (26264, 'love0totsravel');
insert into TRAV_GROUP_MEMBER_BUSINESSES (GROUP_ID, USERNAME)
values (12345, 'gatosgatods');
insert into TRAV_GROUP_MEMBER_BUSINESSES (GROUP_ID, USERNAME)
values (12345, 'coffeeee123');
insert into TRAV_GROUP_MEMBER_BUSINESSES (GROUP_ID, USERNAME)
values (85678, 'lululemon_admin');

insert into BUS_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (22222, 'meme009');
insert into BUS_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (11111, 'weaselyy7');
insert into BUS_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (33333, 'letorysi');
insert into BUS_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (55555, 'meme009');
insert into BUS_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (22222, 'letorysi');
insert into BUS_GROUP_MEMBER_TRAVELLERS (GROUP_ID, USERNAME)
values (22222, 'erdistro');

insert into BUS_GROUP_MEMBER_BUSINESSES (GROUP_ID, USERNAME)
values (11111, 'love0totsravel');
insert into BUS_GROUP_MEMBER_BUSINESSES (GROUP_ID, USERNAME)
values (11111, 'coffeeee123');
insert into BUS_GROUP_MEMBER_BUSINESSES (GROUP_ID, USERNAME)
values (55555, 'skyyscanner08');
insert into BUS_GROUP_MEMBER_BUSINESSES (GROUP_ID, USERNAME)
values (33333, 'lululemon_admin');
insert into BUS_GROUP_MEMBER_BUSINESSES (GROUP_ID, USERNAME)
values (22222, 'lululemon_admin');

insert into TRAV_GROUP_INTERESTS (GROUP_ID, INTEREST_NAME)
values (84737, 'Restaurants');
insert into TRAV_GROUP_INTERESTS (GROUP_ID, INTEREST_NAME)
values (00001, 'Museums & Art Galleries');
insert into TRAV_GROUP_INTERESTS (GROUP_ID, INTEREST_NAME)
values (12345, 'Make Friends');
insert into TRAV_GROUP_INTERESTS (GROUP_ID, INTEREST_NAME)
values (26264, 'Restaurants');
insert into TRAV_GROUP_INTERESTS (GROUP_ID, INTEREST_NAME)
values (85678, 'Backpacking');

insert into BUS_GROUP_INTERESTS (GROUP_ID, INTEREST_NAME)
values (11111, 'Sightseeing');
insert into BUS_GROUP_INTERESTS (GROUP_ID, INTEREST_NAME)
values (22222, 'Restaurants');
insert into BUS_GROUP_INTERESTS (GROUP_ID, INTEREST_NAME)
values (33333, 'Backpacking');
insert into BUS_GROUP_INTERESTS (GROUP_ID, INTEREST_NAME)
values (33333, 'Sightseeing');
insert into BUS_GROUP_INTERESTS (GROUP_ID, INTEREST_NAME)
values (44444, 'Wellness');
insert into BUS_GROUP_INTERESTS (GROUP_ID, INTEREST_NAME)
values (55555, 'Sightseeing');

insert into TRAV_GRP_TRIP (TRIP_ID, GROUP_ID, MEETING_LOCATION, TITLE, GROUP_LIMIT, TRIP_COST, CURRENCY, DESCRIPTION, START_DATE, END_DATE)
values (0001, 84737, 'Vancouver', 'Vancouver Foodie Tour', 7, 199.99, 'CAD', 'Let''s visit the most desirable restaurants in Metro Vancouver', '2020-03-02', NULL);
insert into TRAV_GRP_TRIP (TRIP_ID, GROUP_ID, MEETING_LOCATION, TITLE, GROUP_LIMIT, TRIP_COST, CURRENCY, DESCRIPTION, START_DATE, END_DATE)
values (0002, 85678, 'Madrid', 'Camino de Santiago', 5, 2000, 'EUR', 'Walking the Camino de Santiago', '2020-05-01', '2020-05-30');
insert into TRAV_GRP_TRIP (TRIP_ID, GROUP_ID, MEETING_LOCATION, TITLE, GROUP_LIMIT, TRIP_COST, CURRENCY, DESCRIPTION, START_DATE, END_DATE)
values (0003, 00001, 'London', 'Art Near Me', 3, 400, 'GBP', 'Visiting Museums in London', '2020-03-20', '2020-03-23');
insert into TRAV_GRP_TRIP (TRIP_ID, GROUP_ID, MEETING_LOCATION, TITLE, GROUP_LIMIT, TRIP_COST, CURRENCY, DESCRIPTION, START_DATE, END_DATE)
values (0004, 26264, 'Taipei', 'Tackling Taipei', 9, 5000, 'TWD', 'Visiting the best that Taipei has to offer', '2020-05-23', '2020-05-24');
insert into TRAV_GRP_TRIP (TRIP_ID, GROUP_ID, MEETING_LOCATION, TITLE, GROUP_LIMIT, TRIP_COST, CURRENCY, DESCRIPTION, START_DATE, END_DATE)
values (0005, 12345, 'University of Washington', 'ZZZZZ', 1, 0, 'USD', 'One-on-one tour of the best napping spots on campus! Book now before it''s gone!', NULL, NULL);

insert into BUS_GRP_TRIP (TRIP_ID, GROUP_ID, MEETING_LOCATION, TITLE, GROUP_LIMIT, TRIP_COST, CURRENCY, DESCRIPTION, START_DATE, END_DATE)
values (0001, 44444, 'Kitsilano Beach', 'Sunset Yoga', 30, 0, 'CAD', 'Let''s do some yoga at sunset! Meet at 5pm.', '2020-02-28', NULL);
insert into BUS_GRP_TRIP (TRIP_ID, GROUP_ID, MEETING_LOCATION, TITLE, GROUP_LIMIT, TRIP_COST, CURRENCY, DESCRIPTION, START_DATE, END_DATE)
values (0002, 33333, 'Tofino', 'Witness Winter', 15, 300, 'CAD', 'You''ve been in Tofino for the summer, but have you for the winter? Explore Tofino in winter and witness the majestic seaside storms.', '2020-12-18', '2020-12-20');
insert into BUS_GRP_TRIP (TRIP_ID, GROUP_ID, MEETING_LOCATION, TITLE, GROUP_LIMIT, TRIP_COST, CURRENCY, DESCRIPTION, START_DATE, END_DATE)
values (0003, 22222, 'Vancouver', 'Roaster Tour', 10, 20, 'CAD', 'Join us on a tour of one of Vancouver''s finest coffee roasters!', '2020-04-12', '2020-04-12');
insert into BUS_GRP_TRIP (TRIP_ID, GROUP_ID, MEETING_LOCATION, TITLE, GROUP_LIMIT, TRIP_COST, CURRENCY, DESCRIPTION, START_DATE, END_DATE)
values (0004, 55555, 'SFU', 'Campus Scenes', 20, 40, 'CAD', 'Visit the famed film locations around campus as seen in Fantastic Four, Catwoman, and more! Guided tour.', '2020-05-01', '2020-05-01');
insert into BUS_GRP_TRIP (TRIP_ID, GROUP_ID, MEETING_LOCATION, TITLE, GROUP_LIMIT, TRIP_COST, CURRENCY, DESCRIPTION, START_DATE, END_DATE)
values (0005, 22222, 'Vancouver', 'Coffee Buffet', 10, 50, 'CAD', 'Sample a large selection of different coffee-based drinks, including upcoming hits!', '2020-04-30', '2020-04-30');

insert into TRAV_GRP_TRP_ACTIVITY (ACTIVITY_ID, TRIP_ID, MEETING_LOCATION, TITLE, ACTIVITY_COST, DESCRIPTION, START_DATE, END_DATE)
values (999, 0002, 'Santander', 'Sangria Party', 3, 'Meet fellow Caminos', '2020-05-05', NULL);
insert into TRAV_GRP_TRP_ACTIVITY (ACTIVITY_ID, TRIP_ID, MEETING_LOCATION, TITLE, ACTIVITY_COST, DESCRIPTION, START_DATE, END_DATE)
values (888, 0002, 'Barcelona', 'Take a break from walking', 200, 'Barcelona Sightseeing', '2020-05-14', '2020-05-16');
insert into TRAV_GRP_TRP_ACTIVITY (ACTIVITY_ID, TRIP_ID, MEETING_LOCATION, TITLE, ACTIVITY_COST, DESCRIPTION, START_DATE, END_DATE)
values (666, 0004, 'Taipei', 'Visit the National Palace Museum', 100, 'NPM has a Permanent collection of nearly 700,000 pieces of ancient Chinese imperial artifacts and artwork.', '2020-05-23', NULL);
insert into TRAV_GRP_TRP_ACTIVITY (ACTIVITY_ID, TRIP_ID, MEETING_LOCATION, TITLE, ACTIVITY_COST, DESCRIPTION, START_DATE, END_DATE)
values (333, 0003, 'London', 'Visit the British Museum', 0, 'Welcome to the British Museum – discover two million years of human history and culture.', '2020-03-20', '2020-03-20');
insert into TRAV_GRP_TRP_ACTIVITY (ACTIVITY_ID, TRIP_ID, MEETING_LOCATION, TITLE, ACTIVITY_COST, DESCRIPTION, START_DATE, END_DATE)
values (334, 0003, 'London', 'Tea at the British Museum', 10, 'Enjoy a spot of tea at Oliver''s Tearoom.', '2020-03-20', '2020-03-20');

insert into BUS_GRP_TRP_ACTIVITY (ACTIVITY_ID, TRIP_ID, MEETING_LOCATION, TITLE, ACTIVITY_COST, DESCRIPTION, START_DATE, END_DATE)
values (555, 0005, 'Vancouver', 'Ice cream after yoga', 10, 'Let''s grab ice cream after the workout! Anyone is welcome!', '2020-02-28', NULL);
insert into BUS_GRP_TRP_ACTIVITY (ACTIVITY_ID, TRIP_ID, MEETING_LOCATION, TITLE, ACTIVITY_COST, DESCRIPTION, START_DATE, END_DATE)
values (668, 0002, 'Tofino', 'Kayaking', 100, 'Guided kayak tour. Kayakers of all experience levels welcome!', '2020-12-18', '2020-12-18');
insert into BUS_GRP_TRP_ACTIVITY (ACTIVITY_ID, TRIP_ID, MEETING_LOCATION, TITLE, ACTIVITY_COST, DESCRIPTION, START_DATE, END_DATE)
values (666, 0002, 'Chesterman Beach', 'Surfing', 100, 'Catch some waves! Wetsuits and boards included.', '2020-12-19', '2020-12-19');
insert into BUS_GRP_TRP_ACTIVITY (ACTIVITY_ID, TRIP_ID, MEETING_LOCATION, TITLE, ACTIVITY_COST, DESCRIPTION, START_DATE, END_DATE)
values (667, 0002, 'Nanaimo', 'Oyster Picking', 0, 'Harvest wild oysters. Fishing licence required.', '2020-12-20', '2020-12-20');
insert into BUS_GRP_TRP_ACTIVITY (ACTIVITY_ID, TRIP_ID, MEETING_LOCATION, TITLE, ACTIVITY_COST, DESCRIPTION, START_DATE, END_DATE)
values (777, 0003, 'Vancouver', 'Roast Your Own', 0, 'Roast your own beans! Purchase beans at the roastery or bring your own.', NULL, NULL);

insert into TRAV_GROUP_SPONSORED_DEALS (PROMO_CODE, BUSINESS_USERNAME, GROUP_ID, CONDITION, DEAL)
values ('BOGOTEA', 'coffeeee123', 84737, 'Second drink must be of equal or lesser value', 'Buy one tea drink and get a second free');
insert into TRAV_GROUP_SPONSORED_DEALS (PROMO_CODE, BUSINESS_USERNAME, GROUP_ID, CONDITION, DEAL)
values ('APPOFFER', 'coffeeee123', 84737, 'Download our app! Available on Android and iOS.', 'Get one drink 50% off.');
insert into TRAV_GROUP_SPONSORED_DEALS (PROMO_CODE, BUSINESS_USERNAME, GROUP_ID, CONDITION, DEAL)
values ('REFERAFRIEND', 'gatosgatods', 12345, 'Refer a new user. Must subscribe for at least one paid month.', 'Get one month of credits towards your favourite shows!');
insert into TRAV_GROUP_SPONSORED_DEALS (PROMO_CODE, BUSINESS_USERNAME, GROUP_ID, CONDITION, DEAL)
values ('BINGEMASTER', 'gatosgatods', 12345, 'Watch every episode of a multi-seasoned TV series within a week', 'In-group shoutout');
insert into TRAV_GROUP_SPONSORED_DEALS (PROMO_CODE, BUSINESS_USERNAME, GROUP_ID, CONDITION, DEAL)
values ('COFFEEFRIENDS', 'coffeeee123', 12345, 'Bring a friend when you visit', 'Complimentary dessert');

insert into BUS_GRP_SPONSORED_DEALS (PROMO_CODE, BUSINESS_USERNAME, GROUP_ID, CONDITION, DEAL)
values ('FREENOW', 'coffeeee123', 22222, NULL, 'Free coffee for all travel group members');
insert into BUS_GRP_SPONSORED_DEALS (PROMO_CODE, BUSINESS_USERNAME, GROUP_ID, CONDITION, DEAL)
values ('FREEBUNNOW', 'coffeeee123', 22222, NULL, 'Free cinnamon bun for all travel group members');
insert into BUS_GRP_SPONSORED_DEALS (PROMO_CODE, BUSINESS_USERNAME, GROUP_ID, CONDITION, DEAL)
values ('LEGGINMARCH', 'lululemon_admin', 44444, 'Must have participated in at least 3 trips to qualify', 'Free Lululemon leggings for participants');
insert into BUS_GRP_SPONSORED_DEALS (PROMO_CODE, BUSINESS_USERNAME, GROUP_ID, CONDITION, DEAL)
values ('TRAVELTODAY', 'skyyscanner08', 11111, 'Domestic flights & Economy seats only', '10% discount on flight tickets');
insert into BUS_GRP_SPONSORED_DEALS (PROMO_CODE, BUSINESS_USERNAME, GROUP_ID, CONDITION, DEAL)
values ('YOGA123', 'lululemon_admin', 44444, 'Cannot be combined with another promo', 'Free yoga lesson');

insert into TRAV_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0001, 'Chinese Food');
insert into TRAV_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0002, 'Sightseeing');
insert into TRAV_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0003, 'Museums & Art Galleries');
insert into TRAV_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0004, 'Bar Hopping');
insert into TRAV_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0004, 'Restaurants');
insert into TRAV_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0005, 'Wellness');
insert into TRAV_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0005, 'Make Friends');

insert into BUS_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0001, 'Wellness');
insert into BUS_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0001, 'Sightseeing');
insert into BUS_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0002, 'Sightseeing');
insert into BUS_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0002, 'Outdoor Activity');
insert into BUS_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0003, 'Restaurants');
insert into BUS_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0004, 'Sightseeing');
insert into BUS_GRP_TRIP_INTERESTS (TRIP_ID, INTEREST_NAME)
values (0005, 'Restaurants');

insert into TRAV_GRP_TRP_TRAVELLER_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (3340932, 'Meiji Sushi CLOSED', 'Hi everyone. The sushi place we wanted to go to is NOW CLOSED! Does anyone know a great sushi restaurant?', '2020-02-29 21:21:21', 'letorysi', 0001);
insert into TRAV_GRP_TRP_TRAVELLER_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (3397732, 'Suggestions?', 'Should we take the seabus or the bus to North Van?', '2020-01-05 22:22:22', 'meme009', 0001);
insert into TRAV_GRP_TRP_TRAVELLER_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (7340444, 'How hot is Taipei in May?', 'I’m taking 2 t-shirts for this trip. Do you think that’s enough clothes for Taipei in May?', '2020-04-01 09:07:05', 'ucivermi32', 0004);
insert into TRAV_GRP_TRP_TRAVELLER_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (0000932, 'Family emergency!', 'Sorry, I’ve gotta bail - family emergency. See you next time!', '2020-02-28 17:58:24', 'oberberi', 0005);
insert into TRAV_GRP_TRP_TRAVELLER_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (3276498, 'Do I need to know Spanish?', NULL, '2020-02-28 15:01:57', 'meme009', 0002);
insert into TRAV_GRP_TRP_TRAVELLER_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (4340989, 'Is GfC okay?', 'Did you see those posts earlier...? They get a new intern or something?', '2020-03-01 20:20:20', 'meme009', 0001);

insert into BUS_GRP_TRP_BUSINESS_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (5552242, 'GIVEAWAY! FREE CINNAMON BUNS', 'First 10 people to comment when we were first founded will win free cinnamon buns!', '2020-01-23 00:12:35', 'coffeeee123', 0005);
insert into BUS_GRP_TRP_BUSINESS_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (2323590, 'Your favourite part of Wednesdays?', 'We love that the week is halfway done!', '2020-09-21 23:59:59', 'love0totsravel', 0002);
insert into BUS_GRP_TRP_BUSINESS_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (1231231, 'What are you gonna wear?', 'We love Wunder Under leggings!', '2021-09-23 12:34:56', 'lululemon_admin', 0005);
insert into BUS_GRP_TRP_BUSINESS_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (5326785, '2 more days!!!!!', 'Who’s as pumped as we are??', '2019-04-29 11:11:11', 'gatosgatods', 0004);
insert into BUS_GRP_TRP_BUSINESS_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (9808098, 'Which city should we go to next?', NULL, '2020-11-15 14:15:16', 'skyyscanner08', 0001);

insert into TRAV_GRP_TRP_BUSINESS_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (0983454, 'Yo, check out our newest deal!', 'This is perfect for friends visiting our UW campus location! See deal COFFEEFRIENDS for more info.', '2020-02-22 22:22:22', 'coffeeee123', 0005);
insert into TRAV_GRP_TRP_BUSINESS_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (4340985, 'Hi! Coffee anyone? ☕', 'We may not be a restaurant, but if you find yourself craving a cuppa...you know where to find us ;)', '2020-03-01 12:03:22', 'coffeeee123', 0001);
insert into TRAV_GRP_TRP_BUSINESS_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (4340986, 'A cup of joe just hits the spot...', 'We''ve got some great offers ongoing! Don''t miss this opportunity. While stocks last.', '2020-03-01 14:11:43', 'coffeeee123', 0001);
insert into TRAV_GRP_TRP_BUSINESS_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (4340987, 'Anyone there? :c', 'No one seems to be responding. It''s a bit lonely D:', '2020-03-01 15:54:33', 'coffeeee123', 0001);
insert into TRAV_GRP_TRP_BUSINESS_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (4340988, '???????????????', 'I''m becoming desperate...please visit us? :))', '2020-03-01 16:00:00', 'coffeeee123', 0001);

insert into BUS_GRP_TRP_TRAVELLER_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (3049589, 'Hey...sorry if not allowed, but...', 'Do you guys feel like this group''s trips have been going downhill? The latest Coffee ~~Buffet~~ is way overpriced for what it is. Meh.', '2020-04-15 15:23:33', 'letorysi', 0005);
insert into BUS_GRP_TRP_TRAVELLER_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (3454945, 'I''m so excited!!! Aaaahhhh <3333333', 'This trip sounds so fun!!!! I''m going to roast my beans to CINDERS \o/ dark roast best roast 🔥🔥🔥', '2020-04-12 02:23:44', 'meme009', 0003);
insert into BUS_GRP_TRP_TRAVELLER_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (3454946, 'Send help please', 'Just saw the buffet price...$50...wow. WOW. I haven''t missed a single trip so far and I don''t want to lost my special status :(', '2020-04-16 04:00:34', 'meme009', 0005);
insert into BUS_GRP_TRP_TRAVELLER_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (3485988, 'Let''s go to UBC next!! 😍😍😍', 'I heard the clock tower is a good spot?? Do you think we can go up?', '2020-05-02 03:23:33', 'meme009', 0004);
insert into BUS_GRP_TRP_TRAVELLER_FORUM_POSTS (POST_ID, TITLE, BODY, TIME_POSTED, AUTHOR_USERNAME, TRIP_ID)
values (7650984, 'So...I haven''t surfed before', 'Is it dangerous? Especially in the winter? It''s been ages since I took swimming lessons. Worried it''ll be too cold.', '2020-10-23 17:35:33', 'letorysi', 0002);

insert into TRAV_GRP_TRP_TRAV_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3340932, 0001, 'California Sushi on Broadway is pretty good.', '2020-02-29 12:23:57', 'meme009');
insert into TRAV_GRP_TRP_TRAV_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3340932, 0002, 'Sushi Gallery at Point Grey is amazing also!', '2020-03-01 04:34:26', 'meme009');
insert into TRAV_GRP_TRP_TRAV_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (0000932, 0005, 'I hope everything is alright! See you soon :)', '2020-02-28 07:36:35', 'meme009');
insert into TRAV_GRP_TRP_TRAV_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (7340444, 0004, 'I think you should pack a cardigan. The temperature can drop at night.', '2020-04-01 01:25:33', 'weaselyy7');
insert into TRAV_GRP_TRP_TRAV_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3276498, 0006, 'Yes. You should at least know a couple of phrases in Spanish.', '2020-03-01 22:00:13', 'oberberi');

insert into BUS_GRP_TRP_TRAV_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3454946, 0001, 'ty for the quick response!! i hope this works out 🙏', '2020-04-17 00:55:41', 'meme009');
insert into BUS_GRP_TRP_TRAV_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3454945, 0001, 'yes see you!!', '2020-04-12 08:30:23', 'meme009');
insert into BUS_GRP_TRP_TRAV_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3049589, 0001, 'I''m relieved. Thanks for listening :)', '2020-04-15 19:23:41', 'letorysi');
insert into BUS_GRP_TRP_TRAV_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3049589, 0002, 'praise be the coffee gods 🙌🙌🙌', '2020-04-16 01:23:55', 'meme009');
insert into BUS_GRP_TRP_TRAV_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (7650984, 0001, 'That''s reassuring. Thanks!', '2020-10-24 11:34:00', 'letorysi');

insert into TRAV_GRP_TRP_TRAV_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3397732, 0001, 'The Seabus sounds like fun!', '2020-01-06 07:11:11', 'love0totsravel');
insert into TRAV_GRP_TRP_TRAV_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (7340444, 0001, 'We recommend dressing in layers.', '2020-04-01 10:33:44', 'love0totsravel');
insert into TRAV_GRP_TRP_TRAV_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3276498, 0001, 'It couldn''t hurt! Then you can chat with the locals too!', '2020-02-28 15:30:22', 'lululemon_admin');
insert into TRAV_GRP_TRP_TRAV_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3276498, 0002, 'If you want to learn, Duolingo is a great resource!', '2020-02-28 15:31:12', 'lululemon_admin');
insert into TRAV_GRP_TRP_TRAV_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (4340989, 0001, 'Yes! Sorry about that! My deepest apologies.', '2020-03-04 08:39:00', 'coffeeee123');

insert into BUS_GRP_TRP_TRAV_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3049589, 0001, 'We''re very sorry to hear that! We''ll work with our team to improve future trips. Thanks for the feedback!', '2020-04-15 16:02:25', 'coffeeee123');
insert into BUS_GRP_TRP_TRAV_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3454945, 0001, 'Us too!! See you there!', '2020-04-12 08:30:22', 'coffeeee123');
insert into BUS_GRP_TRP_TRAV_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3454946, 0001, 'Hi, meme009! Please rest assured that we are working to resolve this issue. Thank you for the continued support!', '2020-04-16 08:23:55', 'coffeeee123');
insert into BUS_GRP_TRP_TRAV_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (3485988, 0001, 'Great idea! In fact...it''s already in the works ;)', '2020-05-02 09:34:44', 'gatosgatods');
insert into BUS_GRP_TRP_TRAV_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (7650984, 0001, 'No worries! You''ll be accompanied at all times by a certified instructor. And the wetsuits are more than enough to protect you from the elements.', '2020-10-23 17:44:44', 'love0totsravel');

insert into TRAV_GRP_TRP_BUS_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (4340988, 0001, 'um...hey...', '2020-03-02 01:23:11', 'meme009');
insert into TRAV_GRP_TRP_BUS_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (4340988, 0002, 'you ok man?', '2020-03-02 01:23:43', 'meme009');
insert into TRAV_GRP_TRP_BUS_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (4340988, 0003, 'It''s been a day since they posted anything. I''m worried. Should we contact the company?', '2020-03-03 14:45:22', 'erdistro');
insert into TRAV_GRP_TRP_BUS_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (4340988, 0004, ':(', '2020-03-03 15:23:52', 'oberberi');
insert into TRAV_GRP_TRP_BUS_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (4340988, 0005, 'hi!! that''s good to hear', '2020-03-04 01:33:56', 'meme009');

-- TODO: running out of space below here

insert into BUS_GRP_TRP_BUS_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (5552242, 0001, 'Er...2000? Idk. ¯\_(ツ)_/¯', '2020-01-23 00:20:38', 'meme009');
insert into BUS_GRP_TRP_BUS_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (5552242, 0002, 'It''s on their website. 1993. But you didn''t hear it from me 😉', '2020-01-23 00:34:11', 'letorysi');
insert into BUS_GRP_TRP_BUS_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (5552242, 0003, 'Oh, well in that case, 1993 ofc! Thanks mate', '2020-01-23 00:59:28', 'meme009');
insert into BUS_GRP_TRP_BUS_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (5552242, 0004, 'Np <3 happy to help!', '2020-01-23 09:23:16', 'letorysi');
insert into BUS_GRP_TRP_BUS_POST_TRAVELLER_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (5552242, 0005, 'Whoa, save some for me!', '2020-01-23 10:10:10', 'erdistro');

insert into TRAV_GRP_TRP_BUS_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (4340988, 0001, 'sorry for worrying you guys!! i''m ok now c:', '2020-03-03 20:34:11', 'coffeeee123');
insert into TRAV_GRP_TRP_BUS_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (4340988, 0002, 'thank you for the concern everyone it''s greatly appreciated', '2020-03-04 08:34:00', 'coffeeee123');
insert into TRAV_GRP_TRP_BUS_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (4340987, 0001, 'i''m ok now don''t worry!!', '2020-03-04 08:37:04', 'coffeeee123');
insert into TRAV_GRP_TRP_BUS_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (4340988, 0003, 'Glad to have you back 😀', '2020-03-04 09:33:44', 'love0totsravel');
insert into TRAV_GRP_TRP_BUS_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (4340988, 0004, 'Aww thank you!', '2020-03-04 12:00:00', 'coffeeee123');

insert into BUS_GRP_TRP_BUS_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (5552242, 0001, 'Aaaand we''re all out, folks! Thanks for participating and congrats to the winners 🎉 The answer was: 1993! Yup, we''re almost three decades old!', '2020-03-05 10:33:26', 'coffeeee123');
insert into BUS_GRP_TRP_BUS_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (5552242, 0002, 'Keep your eyes peeled for more contests like this!', '2020-03-05 10:40:33', 'coffeeee123');
insert into BUS_GRP_TRP_BUS_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (9808098, 0001, 'The Caribbean sounds good this time of year. What do you think?', '2020-11-15 14:22:11', 'skyyscanner08');
insert into BUS_GRP_TRP_BUS_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (5326785, 0001, 'We are!! 😄', '2020-04-29 13:34:19', 'skyyscanner08');
insert into BUS_GRP_TRP_BUS_POST_BUSINESS_COMMENTS (POST_ID, COMMENT_ID, BODY, TIME_COMMENTED, AUTHOR_USERNAME)
values (5326785, 0002, 'High five!', '2020-04-29 13:39:35', 'gatosgatods');
