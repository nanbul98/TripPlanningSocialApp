create table currency
(
    Code char(3), -- ISO 4217
    primary key (Code)
);

create table country_display_currency
(
    Country  char(30),
    Currency char(3),
    primary key (Country),
    foreign key (Currency) references currency (Code)
        on delete set null
);

create table traveller
(
    Username  char(30),
    Name      char(30),
    Country   char(30),
    Province  char(30),
    City      char(30),
    Gender    char(15),
    Birthdate date,
    primary key (Username),
    foreign key (Country) references country_display_currency
        on delete set null
);

create table business
(
    Username char(30),
    Name     char(30),
    Country  char(30),
    Province char(30),
    City     char(30),
    primary key (Username),
    foreign key (Country) references country_display_currency
        on delete set null
);

create table traveller_bucket_lists
(
    Username char(30),
    Title    char(30),
    primary key (Username, Title),
    foreign key (Username) references traveller
        on delete cascade
);

create table interest
(
    Name        char(30),
    Description varchar(250 char),
    primary key (Name)
);

create table bucket_list_interests
(
    Username          char(30),
    Bucket_List_Title char(30),
    Interest_Name     char(30),
    primary key (Username, Bucket_List_Title, Interest_Name),
    foreign key (Username, Bucket_List_Title) references traveller_bucket_lists (Username, Title)
        on delete cascade,
    foreign key (Interest_Name) references interest (Name)
        on delete cascade
);

create table traveller_group
(
    Group_ID       integer,
    Title          char(30),
    Description    varchar(250 char),
    Owner_Username char(30),
    primary key (Group_ID),
    foreign key (Owner_Username) references traveller (Username)
        on delete cascade
);

create table business_group
(
    Group_ID       integer,
    Title          char(30),
    Description    varchar(250 char),
    Owner_Username char(30),
    primary key (Group_ID),
    foreign key (Owner_Username) references business (Username)
        on delete cascade
);

create table trav_group_member_travellers
(
    Group_ID integer,
    Username char(30),
    primary key (Group_ID, Username),
    foreign key (Group_ID) references traveller_group
        on delete cascade,
    foreign key (Username) references traveller
    on delete cascade
);

create table bus_group_member_travellers
(
    Group_ID integer,
    Username char(30),
    primary key (Group_ID, Username),
    foreign key (Group_ID) references business_group
        on delete cascade,
    foreign key (Username) references traveller
        on delete cascade
);

create table trav_group_member_businesses
(
    Group_ID integer,
    Username char(30),
    primary key (Group_ID, Username),
    foreign key (Group_ID) references traveller_group
        on delete cascade,
    foreign key (Username) references business
    on delete cascade
);

create table bus_group_member_businesses
(
    Group_ID integer,
    Username char(30),
    primary key (Group_ID, Username),
    foreign key (Group_ID) references business_group
        on delete cascade,
    foreign key (Username) references business
        on delete cascade
);

create table trav_group_interests
(
    Group_ID      integer,
    Interest_Name char(30),
    primary key (Group_ID, Interest_Name),
    foreign key (Group_ID) references traveller_group
        on delete cascade,
    foreign key (Interest_Name) references interest (Name)
        on delete cascade
);

create table bus_group_interests
(
    Group_ID      integer,
    Interest_Name char(30),
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
    Meeting_Location varchar(250 char),
    Title            char(30),
    Group_Limit      integer,
    Trip_Cost        number,
    Currency         char(3),
    Description      varchar(250 char),
    Start_Date       date,
    End_Date         date,
    primary key (Trip_ID),
    foreign key (Group_ID) references traveller_group
        on delete cascade,
    foreign key (Currency) references currency (Code)
);

create table bus_grp_trip
(
    Trip_ID          integer,
    Group_ID         integer not null,
    Meeting_Location varchar(250 char),
    Title            char(30),
    Group_Limit      integer,
    Trip_Cost        number,
    Currency         char(3),
    Description      varchar(250 char),
    Start_Date       date,
    End_Date         date,
    primary key (Trip_ID),
    foreign key (Group_ID) references business_group
        on delete cascade,
    foreign key (Currency) references currency (Code)
);

create table trav_grp_trp_activity
(
    Activity_ID      integer,
    Trip_ID          integer not null,
    Meeting_Location varchar(250 char),
    Title            char(30),
    Activity_Cost    number,
    Description      varchar(250 char),
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
    Meeting_Location varchar(250 char),
    Title            char(30),
    Activity_Cost    number,
    Description      varchar(250 char),
    Start_Date       date,
    End_Date         date,
    primary key (Activity_ID),
    foreign key (Trip_ID) references bus_grp_trip
        on delete cascade
);

create table trav_group_sponsored_deals
(
    Promo_Code        char(100),
    Business_Username char(30) not null,
    Group_ID          integer not null,
    Condition         varchar(250 char),
    Deal              varchar(250 char),
    primary key (Promo_Code),
    foreign key (Business_Username) references business (Username)
        on delete cascade,
    foreign key (Group_ID) references traveller_group
        on delete cascade
);

create table bus_grp_sponsored_deals
(
    Promo_Code        char(100),
    Business_Username char(30) not null,
    Group_ID          integer not null,
    Condition         varchar(250 char),
    Deal              varchar(250 char),
    primary key (Promo_Code),
    foreign key (Business_Username) references business (Username)
        on delete cascade,
    foreign key (Group_ID) references business_group
        on delete cascade
);

create table trav_grp_trip_interests
(
    Trip_ID       integer,
    Interest_Name char(30),
    primary key (Trip_ID, Interest_Name),
    foreign key (Trip_ID) references trav_grp_trip
        on delete cascade,
    foreign key (Interest_Name) references interest (Name)
        on delete cascade
);

create table bus_grp_trip_interests
(
    Trip_ID       integer,
    Interest_Name char(30),
    primary key (Trip_ID, Interest_Name),
    foreign key (Trip_ID) references bus_grp_trip
        on delete cascade,
    foreign key (Interest_Name) references interest (Name)
        on delete cascade
);

create table trav_grp_trp_traveller_forum_posts
(
    Post_ID         integer,
    Title           char(30),
    Body            varchar(250 char),
    Time_Posted     timestamp,
    Author_Username char(30),
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
    Title           char(30),
    Body            varchar(250 char),
    Time_Posted     timestamp,
    Author_Username char(30),
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
    Title           char(30),
    Body            varchar(250 char),
    Time_Posted     timestamp,
    Author_Username char(30),
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
    Title           char(30),
    Body            varchar(250 char),
    Time_Posted     timestamp,
    Author_Username char(30),
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
    Body            varchar(250 char),
    Time_Commented  timestamp,
    Author_Username char(30),
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
    Body            varchar(250 char),
    Time_Commented  timestamp,
    Author_Username char(30),
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
    Body            varchar(250 char),
    Time_Commented  timestamp,
    Author_Username char(30),
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
    Body            varchar(250 char),
    Time_Commented  timestamp,
    Author_Username char(30),
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
    Body            varchar(250 char),
    Time_Commented  timestamp,
    Author_Username char(30),
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
    Body            varchar(250 char),
    Time_Commented  timestamp,
    Author_Username char(30),
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
    Body            varchar(250 char),
    Time_Commented  timestamp,
    Author_Username char(30),
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
    Body            varchar(250 char),
    Time_Commented  timestamp,
    Author_Username char(30),
    primary key (Post_ID, Comment_ID),
    foreign key (Post_ID) references bus_grp_trp_business_forum_posts
        on delete cascade,
    foreign key (Author_Username) references business (Username)
        on delete set null
);
