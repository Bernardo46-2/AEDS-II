#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

/**
 * @author Bernardo Marques Fernandes - 774119
 */


// ================================================ Structs ================================================= //

typedef struct{
    int day;
    char* month;
    int month_value;
    int year;
}Date;

typedef struct{
    int app_id;
    char* name;
    Date release_date;
    char* owners;
    int age;
    float price;
    int dlcs;
    char** languages;
    int n_languages;
    char* website;
    bool windows;
    bool mac;
    bool pinguim;
    float upvotes;
    int avg_pt;
    char* developers;
    char** genres;
    int n_genres;
}Game;

typedef struct Node_t{
    struct Node_t* next;
    struct Node_t* prev;
    Game game;
}Node;

typedef struct{
    Node* first;
    Node* last;
    int length;
    int comp;
    int mov;
}LinkedList;


// ====================================================== Headers ===================================================== //

int parseInt(char* str);
float parseFloat(char* str);
bool parseBoolean(char* str);
int roundFloat(float f);

Date new_Date(char* day, char* month, char* year);
int date_compare_to(Date self, Date other);

Game new_Game();
void free_game(Game* game);
Game clone_game(Game* game);
char** split(char* str, int arraySize);
Game read_game(char* str);
void print_game(Game game);
int game_compare_to(Game self, Game other);

Node* new_Node();
bool has_next(Node* self);
bool has_prev(Node* self);

LinkedList new_List();
void print_list(LinkedList* self);
void _free_list(Node* self);
void free_list(LinkedList* self);
bool is_empty(LinkedList* self);
void push(LinkedList* self, Game game);
Game pop(LinkedList* self);
void push_front(LinkedList* self, Game game);
Game pop_front(LinkedList* self);
void insert(LinkedList* self, int index, Game game);
Game remove_game(LinkedList* self, int index);
Game get(LinkedList* self, int index);
void _quicksort(LinkedList* self, int left, int right);
void quicksort(LinkedList* self);

bool isFim(char* str);


// =================================================== Functions =================================================== //

int parseInt(char* str){
    int a = 0;
    int b = 1;

    for(int i = strlen(str) - 1; i >= 0; i--, b *= 10){
        a += (str[i] - 48) * b;
    }

    return a;
}

float parseFloat(char* str){
    float value = 0;
    int dot = 0;
    float scale = 1;
    int negative = 0; 

    if(*str == '-'){
        str++;
        negative = 1;
    }
    
    while(*str){
        if(dot){
            scale = scale / 10;
            value = value + (*str - '0')*scale;
        }else{
            if(*str == '.') 
                dot++;
            else
                value = value * 10.0 + (*str - '0');
        }
        
	    str++;
    }
    
    if(negative) return -value;
    else return  value;
}

bool parseBoolean(char* str){
    if(strcmp(str, "True") == 0) return true;
    else if(strcmp(str, "False") == 0) return false;
    else return false;
}

int roundFloat(float f){
    int n = f;

    float dec = f - n;

    if(dec >= .5) n++;
    
    return n; 
}


// =================================================== Date =================================================== //

Date new_Date(char* day, char* month, char* year){
    Date date;

    if(day[0] != '\0'){
        date.day = parseInt(day);
    }else{
        date.day = 1;
    }

    date.month = (char*)malloc(4 * sizeof(char));
    date.month[0] = month[0];
    date.month[1] = month[1];
    date.month[2] = month[2];
    date.month[3] = '\0';

    if(strcmp(date.month, "Jan") == 0) date.month_value = 1;
    else if(strcmp(date.month, "Feb") == 0) date.month_value = 2;
    else if(strcmp(date.month, "Mar") == 0) date.month_value = 3;
    else if(strcmp(date.month, "Apr") == 0) date.month_value = 4;
    else if(strcmp(date.month, "May") == 0) date.month_value = 5;
    else if(strcmp(date.month, "Jun") == 0) date.month_value = 6;
    else if(strcmp(date.month, "Jul") == 0) date.month_value = 7;
    else if(strcmp(date.month, "Aug") == 0) date.month_value = 8;
    else if(strcmp(date.month, "Sep") == 0) date.month_value = 9;
    else if(strcmp(date.month, "Oct") == 0) date.month_value = 10;
    else if(strcmp(date.month, "Nov") == 0) date.month_value = 11;
    else if(strcmp(date.month, "Dec") == 0) date.month_value = 12;

    date.year = parseInt(year);

    return date;
}

int date_compare_to(Date self, Date other){
    int diff = self.year - other.year;

    if(diff == 0) diff = self.month_value - other.month_value;
    if(diff == 0) diff = self.day - other.day;

    return diff;
}


// =================================================== Game =================================================== //

Game new_Game(){
    Game game;

    return game;
}

Game new_Game_2(int app_id, char* name, Date release_date, char* owners, int age, float price, 
               int dlcs, char** languages, int n_languages, char* website, bool windows, bool mac, 
               bool pinguim, float upvotes, int avg_pt, char* developers, char** genres, int n_genres){

    Game game;

    game.app_id = app_id;
    game.name = name;
    game.release_date = release_date;
    game.owners = owners;
    game.age = age;
    game.price = price;
    game.dlcs = dlcs;
    game.languages = languages;
    game.n_languages = n_languages;
    game.website = website;
    game.windows = windows;
    game.mac = mac;
    game.pinguim = pinguim;
    game.upvotes = upvotes;
    game.avg_pt = avg_pt;
    game.developers = developers;
    game.genres = genres;
    game.n_genres = n_genres;

    return game;
}

int game_compare_to(Game self, Game other){
    int diff = date_compare_to(self.release_date, other.release_date);

    if(diff == 0) diff = strcmp(self.name, other.name);

    return diff;
}

void free_game(Game* game){
    if(game->name != NULL) free(game->name);
    if(game->owners != NULL) free(game->owners);

    if(game->languages != NULL){
        for(int i = 0; i < game->n_languages; i++)
            if(game->languages[i] != NULL) free(game->languages[i]);
        free(game->languages);
    }

    if(game->website != NULL) free(game->website);
    if(game->developers != NULL) free(game->developers);

    if(game->genres != NULL){
        for(int i = 0; i < game->n_genres; i++)
            if(game->genres[i] != NULL) free(game->genres[i]);
        free(game->genres);
    }
}

Game clone_game(Game* game){
    Game clone;

    clone.app_id = game->app_id;
    clone.name = game->name;
    clone.release_date = game->release_date;
    clone.owners = game->owners;
    clone.age = game->age;
    clone.price = game->price;
    clone.dlcs = game->dlcs;
    clone.languages = game->languages;
    clone.n_languages = game->n_languages;
    clone.website = game->website;
    clone.windows = game->windows;
    clone.mac = game->mac;
    clone.pinguim = game->pinguim;
    clone.upvotes = game->upvotes;
    clone.avg_pt = game->avg_pt;
    clone.developers = game->developers;
    clone.genres = game->genres;
    clone.n_genres = game->n_genres;

    return clone;
}

char** split(char* str, int arraySize){
    char** array = (char**)malloc(arraySize * sizeof(char*));

    for(int i = 0; i < arraySize; i++){
        array[i] = (char*)malloc(200 * sizeof(char));
    }

    for(int i = 0, index = 0; i < strlen(str) && str[i] != '\"'; i++, index++){
        int j = 0;

        while(i < strlen(str) && str[i] != ','){
            if(j == 0 && str[i] == ' '){
                //do nothing
            }else if(str[i] != '[' && str[i] != ']' && str[i] != '\''){
                array[index][j++] = str[i];
                array[index][j] = '\0';
            }
        
            i++;
        }
    }

    return array;
}

Game read_game(char* str){
    Game game;
    char* value = (char*)malloc(1000 * sizeof(char));
    int i = 0;
    int j = 0;
    char c = ',';

    // =========================== app_id ============================== //
    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }   
    
    game.app_id = parseInt(value);
    
    i++;
    value[0] = '\0';
    j = 0;
    
    
    // =========================== name ============================== //
    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }

    game.name = (char*)malloc(1000 * sizeof(char));
    
    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }

    if(strlen(value) > 0) 
        strcpy(game.name, value);
    else 
        strcpy(game.name, "null");

    value[0] = '\0';
    i++;
    j = 0;
    if(c == '\"') i++;


    // =========================== release_date ============================== //
    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }

    while(str[i] != c){
        value[j++] = str[i++]; 
        value[j] = '\0';
    }

    char day[3];
    char month[4];
    char year[5];

    day[0] = '\0';

    for(int j = 0; j < strlen(value); j++){
        if(value[j] == ','){
            if(value[j - 2] >= '0' && value[j - 2] <= '9'){
                day[0] = value[j - 2];
                day[1] = value[j - 1];
                day[2] = '\0';
            }else{
                day[0] = value[j - 1];
                day[1] = '\0';
                day[2] = '\0';
            }

            j = 10; //break
        }
    }

    month[0] = value[0];
    month[1] = value[1];
    month[2] = value[2];
    month[3] = '\0';

    year[0] = value[strlen(value) - 4];
    year[1] = value[strlen(value) - 3];
    year[2] = value[strlen(value) - 2];
    year[3] = value[strlen(value) - 1];
    year[4] = '\0';

    game.release_date = new_Date(day, month, year);
    
    value[0] = '\0';
    i++;
    j = 0;
    if(c == '\"') i++;

    
    // =========================== owners ============================== //
    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }
    
    game.owners = (char*)malloc(1000 * sizeof(char));

    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }
    
    if(strlen(value) > 0) strcpy(game.owners, value);
    else strcpy(game.owners, "null");

    value[0] = '\0';
    j = 0;
    i++;
    if(c == '\"') i++;


    // =========================== age ============================== //
    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }

    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }

    if(strlen(value) > 0) game.age = parseInt(value);
    else game.age = -1;

    value[0] = '\0';
    j = 0;
    i++;
    if(c == '\"') i++;


    // =========================== price ============================== //
    
    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }

    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }

    if(strlen(value) > 0) game.price = parseFloat(value);
    else game.price = -1;

    value[0] = '\0';
    j = 0;
    i++;
    if(c == '\"') i++;


    // =========================== dlcs ============================== //

    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }

    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }

    if(strlen(value) > 0) game.dlcs = parseInt(value);
    else game.dlcs = -1;
    
    i++;
    value[0] = '\0';
    j = 0;
    if(c == '\"') i++;

    
    // =========================== languages ============================== //

    int arraySize = 1;

    if(game.app_id == 27900){ //hardfix for line 3937
        arraySize = 5;
        i = 146;
        game.languages = (char**)malloc(5 * sizeof(char*));

        game.languages[0] = (char*)malloc(100 * sizeof(char));
        game.languages[1] = (char*)malloc(100 * sizeof(char));
        game.languages[2] = (char*)malloc(100 * sizeof(char));
        game.languages[3] = (char*)malloc(100 * sizeof(char));
        game.languages[4] = (char*)malloc(100 * sizeof(char));

        strcpy(game.languages[0], "English");
        strcpy(game.languages[1], "French");
        strcpy(game.languages[2], "German");
        strcpy(game.languages[3], "Italian");
        strcpy(game.languages[4], "Spanish - Spain");

        game.n_languages = arraySize;
    }else if(game.app_id == 11260){ //hardfix for line 2180
        arraySize = 5;
        i = 129;
        game.languages = (char**)malloc(5 * sizeof(char*));

        game.languages[0] = (char*)malloc(100 * sizeof(char));
        game.languages[1] = (char*)malloc(100 * sizeof(char));
        game.languages[2] = (char*)malloc(100 * sizeof(char));
        game.languages[3] = (char*)malloc(100 * sizeof(char));
        game.languages[4] = (char*)malloc(100 * sizeof(char));

        strcpy(game.languages[0], "English");
        strcpy(game.languages[1], "Russian");
        strcpy(game.languages[2], "Spanish - Spain");
        strcpy(game.languages[3], "Japanese");
        strcpy(game.languages[4], "Czech");

        game.n_languages = arraySize;
    }else{
        if(str[i] == '\"'){
            i++;
        }
        
        if(str[i] == '['){
            c = ']';
            i++;
        }else{
            c = ',';
        }

        while(str[i] != c){
            if(str[i] == ','){
                arraySize++;
            }
            
            value[j++] = str[i++];
            value[j] = '\0';
        }

        game.languages = split(value, arraySize);
        game.n_languages = arraySize;

        value[0] = '\0';
        j = 0;
        
        i++;
        if(c == ']' && arraySize > 1){i++; i++;}
        else if(c == ']'){i++;}
    }
    

    // =========================== website ============================== //
    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }

    game.website = (char*)malloc(1000 * sizeof(char));

    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }

    if(strlen(value) > 0) strcpy(game.website, value);
    else strcpy(game.website, "null");

    value[0] = '\0';
    j = 0;
    i++;
    if(c == '\"') i++;

    
    // =========================== windows ============================== //
    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }
    
    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }

    if(strlen(value) > 0) game.windows = parseBoolean(value);

    value[0] = '\0';
    j = 0;
    i++;
    if(c == '\"');

    
    // =========================== mac ============================== //
    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }

    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }

    if(strlen(value) > 0) game.mac = parseBoolean(value);

    value[0] = '\0';
    j = 0;
    i++;
    if(c == '\"') i++;

    
    // =========================== pinguim ============================== //
    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }
    
    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }
    
    if(strlen(value) > 0) game.pinguim = parseBoolean(value);

    value[0] = '\0';
    j = 0;
    i++;
    if(c == '\"') i++;

    
    // =========================== upvotes ============================== //
    float up1, up2;
    
    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }
    
    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }
    
    up1 = parseFloat(value);

    value[0] = '\0';
    j = 0;
    i++;
    
    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }

    up2 = parseFloat(value);
    
    game.upvotes = (up1 * 100) / (up1 + up2);

    value[0] = '\0';
    j = 0;
    i++;
    if(c == '\"') i++;
    
    
    // =========================== avg_pt ============================== //  
    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }

    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }
    
    if(strlen(value) > 0) game.avg_pt = parseInt(value);
    else game.avg_pt = -1;

    value[0] = '\0';
    j = 0;
    i++;
    if(c == '\"') i++;


    // =========================== developers ============================== //
    if(str[i] == '\"'){
        c = '\"';
        i++;
    }else{
        c = ',';
    }

    game.developers = (char*)malloc(1000 * sizeof(char));

    while(str[i] != c){
        value[j++] = str[i++];
        value[j] = '\0';
    }

    if(strlen(value) > 0) strcpy(game.developers, value);
    else strcpy(game.developers, "null");
    
    value[0] = '\0';
    j = 0;
    i++;
    if(c == '\"') i++;


    // =========================== genres ============================== //
    if(i < strlen(str)){
        arraySize = 1;
        
        if(str[i] == '\"'){
            c = '\"';
            i++;
        }else{
            c = ',';
        }
    
        while(i < strlen(str) && str[i] != c){
            if(str[i] == ','){
                arraySize++;
            }
            
            value[j++] = str[i++];
            value[j] = '\0';
        }
        
        game.genres = split(value, arraySize);
        game.n_genres = arraySize;
    }

    if(value) free(value);

    return game;
}

void print_game(Game game){
    int minutes = game.avg_pt % 60;
    int hours = (game.avg_pt - minutes) / 60;

    printf("%d ", game.app_id);
    printf("%s ", game.name);
    printf("%s/%d ", game.release_date.month, game.release_date.year);
    printf("%s ", game.owners);

    if(game.age != -1) printf("%d ", game.age);
    else printf("null ");
    
    if(game.price != -1) printf("%.2f ", game.price);
    else printf("null");

    if(game.dlcs != -1) printf("%d [", game.dlcs);
    else printf("null [");

    for(int i = 0; i < game.n_languages - 1; i++){
        printf("%s, ", game.languages[i]);
    }

    printf("%s] ", game.languages[game.n_languages - 1]);
    printf("%s ", game.website);
    printf("%s ", game.windows ? "true" : "false");
    printf("%s ", game.mac ? "true" : "false");
    printf("%s ", game.pinguim ? "true" : "false");
    printf("%d%% ", roundFloat(game.upvotes));

    if(hours > 0 && minutes > 0) printf("%dh %dm ", hours, minutes);
    else if(hours == 0 && minutes > 0) printf("%dm ", minutes);
    else if(hours > 0 && minutes == 0) printf("%dh ", hours);
    else if(hours == 0 && minutes == 0) printf("null ");

    printf("%s ", game.developers);

    if(game.n_genres > 0){
        printf("[");
        
        for(int i = 0; i < game.n_genres - 1; i++){
            printf("%s, ", game.genres[i]);
        }

        printf("%s]", game.genres[game.n_genres - 1]);
    }else{
        printf("null");
    }

    printf("\n");
}


// ====================================================== Node ======================================================= //

Node* new_Node(){
    Node* node = (Node*)malloc(sizeof(Node));
    node->prev = NULL;
    node->next = NULL;

    return node;
}

bool has_next(Node* self){
    return self->next != NULL;
}

bool has_prev(Node* self){
    return self->prev != NULL;
}

void _free_node(Node* self){
    if(self != NULL){
        self->next = NULL;
        self->prev = NULL;
        free_game(&(self->game));
        free(self);
    }
}


// =================================================== LinkedList =================================================== //

LinkedList new_List(){
    LinkedList self;

    self.first = new_Node();
    self.last = self.first;
    self.length = 0;
    self.comp = 0;
    self.mov = 0;

    return self;
}

void _free_list(Node* self){
    if(self != NULL) _free_list(self->next);
    _free_node(self);
}

void free_list(LinkedList* self){
    _free_list(self->first->next);
    self->first->next = NULL;
    free(self->first);
}

void print_list(LinkedList* self){
    Node* ptr = self->first->next;

    while(ptr != NULL){
        print_game(ptr->game);
        ptr = ptr->next;
    }
}

bool is_empty(LinkedList* self){
    return self->length == 0;
}

void push(LinkedList* self, Game game){
    Node* tmp = new_Node();
    tmp->game = game;

    self->last->next = tmp;
    tmp->prev = self->last;
    self->last = self->last->next;

    tmp = NULL;
    free(tmp);

    self->length++;
}

Game pop(LinkedList* self){
    if(self->length > 0){
        Game game = self->last->game;
        Node* tmp = self->last;

        self->last = self->last->prev;
        self->last->next->prev = NULL;
        self->last = NULL;
        tmp->prev = NULL;

        free(tmp);
        self->length--;

        return game;
    }

    return new_Game();
}

void push_front(LinkedList* self, Game game){
    Node* tmp = new_Node();
    tmp->game = game;

    tmp->next = self->first->next;
    self->first->next = tmp;
    tmp->prev = self->first;

    if(self->first == self->last) self->last = tmp;
    else tmp->next->prev = tmp;

    tmp = NULL;
    free(tmp);
    
    self->length++;
}

Game pop_front(LinkedList* self){
    if(self->first != self->last){
        Node* tmp = self->first->next;
        Game game = tmp->game;

        self->first->next = tmp->next;
        tmp->next->prev = self->first;
        tmp->next = NULL;
        tmp->prev = NULL;

        free(tmp);
        self->length--;

        return game;
    }

    return new_Game();
}

void insert(LinkedList* self, int index, Game game){
    if(index >= 0 && index < self->length){
        if(index == 0){
            push_front(self, game);
        }else if(index = self->length - 1){
            push(self, game);
        }else{
            Node* ptr = self->first->next;
            Node* tmp = new_Node();
            tmp->game = game;
            int i = 0;
            
            while(i < index && ptr->next != NULL){
                ptr = ptr->next;
                i++;
            }

            tmp->next = ptr->next;
            tmp->prev = ptr;
            tmp->next->prev = tmp;
            tmp->prev->next = tmp;

            tmp = NULL;
            ptr = NULL;
        
            self->length++;
        }
    }
}

Game remove_game(LinkedList* self, int index){
    if(index >= 0 && index < self->length){
        if(index == 0){
            return pop_front(self);
        }else if(index == self->length - 1){
            return pop(self);
        }else{
            Node* ptr = self->first;
            int i = 0;

            while(i < index && ptr->next != NULL){
                ptr = ptr->next;
                i++;
            }

            Node* tmp = ptr->next;
            ptr->next = tmp->next;
            tmp->next->prev = ptr;
            tmp->next = NULL;
            tmp->prev = NULL;

            self->length--;
            Game game = tmp->game;
            free(tmp);

            return game;
        }
    }

    return new_Game();
}

Game get(LinkedList* self, int index){
    if(index >= 0 && index < self->length){
        Node* ptr = self->first->next;
        int i = 0;

        while(i < index && ptr->next != NULL){
            ptr = ptr->next;
            i++;
        }

        Game game = ptr->game;
        ptr = NULL;

        return game;
    }

    return new_Game();
}

void _quicksort(LinkedList* self, int left, int right){
    int i = left;
    int j = right;

    Game pivot = get(self, (left + right) / 2);
    self->mov++;

    Node* ptr1 = self->first->next;
    Node* ptr2 = self->last;

    for(int l = 0; l < left; l++) ptr1 = ptr1->next;
    for(int l = self->length - 1; l > right; l--) ptr2 = ptr2->prev;

    while(i <= j){
        self->comp += 2;

        while(game_compare_to(ptr1->game, pivot) < 0){
            ptr1 = ptr1->next;
            self->comp++;
            i++;
        }

        while(game_compare_to(ptr2->game, pivot) > 0){
            ptr2 = ptr2->prev;
            self->comp++;
            j--;
        }

        if(i <= j){
            Game tmp = ptr1->game;
            ptr1->game = ptr2->game;
            ptr2->game = tmp;

            self->mov += 3;

            ptr1 = ptr1->next;
            ptr2 = ptr2->prev;

            i++;
            j--;
        }
    }

    ptr1 = NULL;
    ptr2 = NULL;

    if(left < j){
        _quicksort(self, left, j);
    }

    if(i < right){
        _quicksort(self, i, right);
    }
}

void quicksort(LinkedList* self){
    _quicksort(self, 0, self->length - 1);
}


// =================================================== Main =================================================== //

bool isFim(char* str){
    return strlen(str) == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M';
}

void matriculaQuicksort(int c, int m, int t){
    FILE* f = fopen("matricula_quicksort2.txt", "w");
    fprintf(f, "774119\t%d\t%d\t%d", c, m, t);
    fclose(f);
}

int main(){
    FILE* file = fopen("/tmp/games.csv", "r");
    
    if(file == NULL) {
        printf("droga! deu erro ;-;"); 
        exit(1);
    }

    Game games[4403];
    LinkedList list = new_List();
    char str[1000];
    bool stop;

    for(int i = 0; i < 4403; i++){
        games[i] = new_Game();

        fscanf(file, " %1000[^\r\n]", str);

        games[i] = read_game(str);
    }

    fclose(file);

    do{
        scanf("%s", str);
        stop = isFim(str);

        if(!stop){
            for(int i = 0; i < 4403; i++){
                if(parseInt(str) == games[i].app_id){
                    push(&list, games[i]);
                }
            }
        }
    }while(!stop);

    clock_t start = clock();
    quicksort(&list);
    clock_t end = clock();
    
    print_list(&list);

    matriculaQuicksort(list.comp, list.mov, end - start);
    free_list(&list);

    return 0;
}
