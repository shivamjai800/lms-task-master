insert into book(id,author,image,title,quantity) values(1,'William Shakespare','BookImage1','Merchant of Venice', 3);
insert into book(id,author,image,title,quantity) values(2,'Paulo Coelho' , 'BookImage2','The Alchemist',4);
insert into book(id,author,image,title,quantity) values(3, 'George Bernard Shaw' ,'BookImage3', 'Arms and the Man', 5);

insert into unit_book(unit_book_id, assigned ,book_parent_id) values('1_1',false,1);
insert into unit_book(unit_book_id, assigned ,book_parent_id) values('1_2',false,1);
insert into unit_book(unit_book_id, assigned ,book_parent_id) values('1_3',false,1);
insert into unit_book(unit_book_id, assigned ,book_parent_id) values('2_1',false,2);
insert into unit_book(unit_book_id, assigned ,book_parent_id) values('2_2',false,2);
insert into unit_book(unit_book_id, assigned ,book_parent_id) values('2_3',false,2);
insert into unit_book(unit_book_id, assigned ,book_parent_id) values('2_4',false,2);
insert into unit_book(unit_book_id, assigned ,book_parent_id) values('3_1',false,3);
insert into unit_book(unit_book_id, assigned ,book_parent_id) values('3_2',false,3);
insert into unit_book(unit_book_id, assigned ,book_parent_id) values('3_3',false,3);
insert into unit_book(unit_book_id, assigned ,book_parent_id) values('3_4',false,3);
insert into unit_book(unit_book_id, assigned ,book_parent_id) values('3_5',false,3);