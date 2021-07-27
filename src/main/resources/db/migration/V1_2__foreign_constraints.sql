alter table book add constraint Book_Uniqueness unique (title, author);
alter table book_record add constraint Book_Record_User foreign key (username) references userr;
alter table book_record add constraint Book_Reference foreign key (book_id) references book;
alter table unit_book add constraint Unit_Book_Book foreign key (book_parent_id) references book;