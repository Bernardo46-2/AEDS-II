#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

/**
 * @author Bernardo Marques Fernandes - 774119
 */

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

    if(dec > 0.5) n++;
    
    return n; 
}

typedef struct Date_s{
    char* month;
    int year;
}Date;

Date new_Date(char* month, char* year){
    Date date;
    date.month = (char*)malloc(4 * sizeof(char));
    
    date.month[0] = month[0];
    date.month[1] = month[1];
    date.month[2] = month[2];
    date.month[3] = '\0';

    date.year = parseInt(year);

    return date;
}

typedef struct Game_s{
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
    bool SistemaDoPinguimLaPorqueOVerdeNaoAceitaUmaVariavelLinux;
    float upvotes;
    int avg_pt;
    char* developers;
    char** genres;
    int n_genres;
}Game;

Game new_Game(){
    Game game;

    return game;
}

Game new_Game_2(int app_id, char* name, Date release_date, char* owners, int age, float price, 
               int dlcs, char** languages, int n_languages, char* website, bool windows, bool mac, 
               bool SistemaDoPinguimLaPorqueOVerdeNaoAceitaUmaVariavelLinux, float upvotes, int avg_pt, char* developers, char** genres, int n_genres){

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
    game.SistemaDoPinguimLaPorqueOVerdeNaoAceitaUmaVariavelLinux = SistemaDoPinguimLaPorqueOVerdeNaoAceitaUmaVariavelLinux;
    game.upvotes = upvotes;
    game.avg_pt = avg_pt;
    game.developers = developers;
    game.genres = genres;
    game.n_genres = n_genres;

    return game;
}

// void freeGame(Game game){
//     if(game != NULL){
//         if(game->name != NULL) free(game->name);
//         if(game->owners != NULL) free(game->owners);

//         if(game->languages != NULL){
//             for(int i = 0; i < game->n_languages; i++)
//                 if(game->languages[i] != NULL) free(game->languages[i]);
//             free(game->languages);
//         }

//         if(game->website != NULL) free(game->website);
//         if(game->developers != NULL) free(game->developers);

//         if(game->genres != NULL){
//             for(int i = 0; i < game->n_genres; i++)
//                 if(game->genres[i] != NULL) free(game->genres[i]);
//             free(game->genres);
//         }

//         free(game);
//     }
// }

Game cloneGame(Game* game){
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
    clone.SistemaDoPinguimLaPorqueOVerdeNaoAceitaUmaVariavelLinux = game->SistemaDoPinguimLaPorqueOVerdeNaoAceitaUmaVariavelLinux;
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

Game readGame(char* str){
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

    char month[4];
    char year[5];

    month[0] = value[0];
    month[1] = value[1];
    month[2] = value[2];
    month[3] = '\0';

    year[0] = value[strlen(value) - 4];
    year[1] = value[strlen(value) - 3];
    year[2] = value[strlen(value) - 2];
    year[3] = value[strlen(value) - 1];
    year[4] = '\0';

    game.release_date = new_Date(month, year);
    
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

    
    // =========================== SistemaDoPinguimLaPorqueOVerdeNaoAceitaUmaVariavelLinux ============================== //
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
    
    if(strlen(value) > 0) game.SistemaDoPinguimLaPorqueOVerdeNaoAceitaUmaVariavelLinux = parseBoolean(value);

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
    
        while(i < strlen(str) - 1 && str[i] != c){
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

void printGame(Game game){
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
    printf("%s ", game.SistemaDoPinguimLaPorqueOVerdeNaoAceitaUmaVariavelLinux ? "true" : "false");
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

bool isFim(char* str){
    return strlen(str) == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M';
}

int main(){
    FILE* file = fopen("../games.csv", "r");
    Game games[4403];
    char str[1000];
    bool stop;

    for(int i = 0; i < 4403; i++){
        games[i] = new_Game();

        fscanf(file, " %1000[^\n]", str);

        games[i] = readGame(str);
    }

    fclose(file);

    do{
        scanf("%s", str);
        stop = isFim(str);

        if(!stop){
            for(int i = 0; i < 4403; i++){
                if(parseInt(str) == games[i].app_id){
                    printGame(games[i]);
                }
            }
        }
    }while(!stop);

    return 0;
}
