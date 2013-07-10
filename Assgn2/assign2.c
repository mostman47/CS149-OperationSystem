#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <time.h> 
#include <stdbool.h>
#define student_number 20	// number of student
#define chair 3	// number of chair
#define max_wait 10 //maximum waiting time
#define max_interval 10//maximum interval time between students' coming
#define time_scale 1 // 1 minute = n second
#define close_time 60// office close after n minute

void *visit_office(void *parm);  // the thread
void chair_queue();
void swap (int chair_index);
void print_table();
int on_chair();

int visited_index = -1;
int visited_remain = 0;
int closed = 1;
int ind = 0;
int time_count = 0;
//Student
struct student
{
	int number;
	int start_time;
	int visit_time;
};
//List of Student
struct student students[student_number];
//List of chair
int chairs[chair];
//Generate student's list
void generate_students()
{
	srand (time(NULL));
	int i = 0;
	int j = 0;
	printf("===========Student List (%d)============\n#\tstart\tvisit\n", student_number);
	for(; i < student_number; i++)
	{
	rand();
	j += (rand()%max_interval);
     students[i].number = (i+1);
	 students[i].start_time = j;
	 students[i].visit_time = rand()%5 + 1;
	 printf("%d\t%d\t%d \n", students[i].number, students[i].start_time,students[i].visit_time);
	}
}

//main
int main(int argc, char *argv[])
{
	
    generate_students();
	//chairs default
	int i;
	for(i = 0; i < chair; i++)
	{
		chairs[i] = -1;
	}
	//
	
	printf("=========Time List=========\n");
	
	//visit
	pthread_t visit_tid;
	pthread_attr_t visit_attr;		
	printf("Parent: Creating visit event thread.\n");
	pthread_attr_init(&visit_attr);
	pthread_create(&visit_tid, &visit_attr, visit_office, argv[1]);
	
	

    pthread_join(visit_tid, NULL);
	
	//
	fflush(stdout);
	exit(0);
}

//chair
void chair_queue()
{		
		while(students[ind].start_time <= time_count && ind < student_number )
		{	
			printf("\t-Student #%d comes to office\n", students[ind].number);
			if(closed == 1)
			{	
				if(visited_index == -1 && chairs[0] == -1)
				{
					visited_index = ind;
					visited_remain = students[visited_index].visit_time;
					//printf("\t-Student #%d doesn't need to wait on chair(%d)\n",students[ind].number,on_chair());
					
				}
				else
				{
					int full = 0;
					int i = 0;
					for(; i < chair; i ++)
					{
						if(chairs[i] == -1)
						{
							chairs[i] = ind;
							full = 1;
							break;
						}
					}
					if(full == 0)
					{				
						printf("\t\t_Because chair-queue is full, student #%d leaves!\n", students[ind].number);
					}
					else
					{
						printf("\t-Student #%d is waiting on chair(%d: %d %d %d)\n",students[ind].number,on_chair()
						,students[chairs[0]].number, students[chairs[1]].number, students[chairs[2]].number);
					}
				}
			}
			else
			{
				printf("\t\t_Because office is closed, student #%d leaves!\n", students[ind].number);
			}
			ind++;
		}
		
		
}
//visit


void *visit_office(void *parm)
{
	while(ind < student_number || visited_index != -1)
	{
		printf("\ntime (in min): %d\n\n", time_count);
		printf("\t\t===Event List===\n");
		chair_queue();
		//print_table();
		printf("\n");
		//
		if(time_count > close_time && visited_index == -1)
		{
			printf("\t+Office is closed\n");
			closed = 0;
			int i = 0;
			for(; i < chair; i ++)
			{
				if(chairs[i] != -1)
				{
					printf("\t\t_Because office is closed, student #%d leaves!\n", students[chairs[i]].number);
					chairs[i] = -1;
				}
			}
		}
		else
		{
			if(visited_index == -1 && chairs[0] == -1)
			{
					printf("\t+Prof. Fore works on the design of her programming language ParFore.\n");
			}
			else
			{
				if(visited_index == -1)
				{
					visited_index = chairs[0];
					visited_remain = students[visited_index].visit_time;
					swap(0);
					//printf("-------- %d %d\n",visited_index, chairs[0]);				
				}
				printf("\t-Student #%d is in office (%d time remaining)\n", students[visited_index].number, visited_remain - 1);
				visited_remain--;
				//printf("---- %d %d %d\n",visited_index, chairs[0],visited_remain);		
				if(visited_remain == 0)
				{
					printf("\t-Student #%d is done. She leaves.\n",students[visited_index].number);
					visited_index = -1;
				}
			}
			sleep(time_scale);// sleep here to let the result appears faster after the office hour
		}
		if(on_chair() > 0)
		{
			int i = 0;
			for(;i < chair; i++)
			{
				if(chairs[i] != -1 && (time_count - students[chairs[i]].start_time )==  max_wait)
				{
					printf("\t\t_Because student #%d waited too long (%d min). She leaves.\n",students[chairs[i]].number,max_wait);
					swap(i);
					i--;
					
				}
			}
		}
		print_table();
		//sleep(time_scale); // may sleep here if want every reasult appears with constant speed 
		time_count++;	
	}
}
void swap (int chair_index)
{
	if(chair_index == 0)
	{
		chairs[0] = chairs[1];
	}
	int i = 1;
	for(;i<chair-1;i++)
	{
		if(chair_index <= i)
		{
			chairs[i] = chairs[i+1];
		}
	}
	chairs[chair-1] = -1;
}

int on_chair()
{
	int i = 0;
	int count = 0;
	for(; i < chair; i ++)
	{
		if(chairs[i] != -1) count ++;
	}
	return count;
}
void print_table()
{
	printf("\n\t\t===Table(time: %d)===\n", time_count);
	printf("\tChair_Queue\t StaT\t VisT\t| Visiting_Office\n");
	if(chairs[0] != -1)
	printf("\t1) #%d\t\t %d\t %d\t|",students[chairs[0]].number,students[chairs[0]].start_time,students[chairs[0]].visit_time);
	else
	printf("\t1) N/A\t\t N/A\t N/a\t|");
	
	if(visited_index != -1)
	printf(" #%d\n", students[visited_index]);
	else
	printf(" N/A\n");
	int i = 1;
	for(; i < chair; i++)
	{
		if(chairs[i] != -1)
		printf("\t%d) #%d\t\t %d\t %d\t|\n",i+1,students[chairs[i]].number,students[chairs[i]].start_time,students[chairs[i]].visit_time);
		else
		printf("\t%d) N/A\t\t N/A\t N/a\t|\n",i+1);
		
		// if(chairs[2] != -1)
		// printf("\t3) %d\t\t|\n",students[chairs[2]]);
		// else
		// printf("\t3) N/A\t\t|\n");
	}
}