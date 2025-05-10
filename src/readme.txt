database:

1.patient table:
    id (primary key)
    name,
    age,
    gender

2.doctor tables:
    id (primary key),
    name,
    department

3.appointment table:
    id (primary key),
    patientid (foreign key),
    doctorid (foreign key),
    appointmentid






classes:

patient:
    add patient,
    view,
    check patient,
    //my functions
    update patient,
    delete patient,

doctor:
    view doctor,
    check doctor,

appointment:
    create appointment,
    view appointment,

hospitalmanagement system:
    driver class,
    main menu,
    exit,