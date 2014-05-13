alter table computer_transcript drop foreign key FK96A1DF5AE04E72A5 ;
delete from processing_queue where status = 'FINISHED'; 
alter table computer_transcript drop foreign key FK96A1DF5AEC9A4979 ;
delete from audio_file where id=2 ;

alter table human_transcript drop foreign key FK67B75E08EC9A4979 ;
delete from audio_file where id in(9,10,11,15,16,17,18,19,20,21,22,23,24,25,26,27); 
delete from audio_file where id in(12,84,13,14,4,5,6,7);

