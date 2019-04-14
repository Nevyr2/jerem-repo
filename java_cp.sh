#!/bin/sh
RED="\033[0;31;1m"    
GREEN="\033[0;32;1m"    
YELLOW="\033[0:33;1m"    
PINK="\033[0:35;1m"    
CYAN="\033[0:36;1m"    
NC="\033[0m"

if [ $# != 1 ]; then
    echo you need to put 1 argument
    echo use : ./java_cp [arg]
else
    echo
    echo ----- COMPILE ALL .java -----:
    echo
    for i in *.java; do
        bad=1
        echo file : $i
        echo 

        javac $i
        
        good=$(echo $i | cut -d'.' -f1)

        for j in *.class; do
            if [ $j = "*.class" ]; then
                bad=1
            else
                check=$(echo $j | cut -d'.' -f1)
                if [ $check = $good ]; then
                    bad=0
                fi
            fi
        done
        if [ $bad -eq 1 ]; then
            echo
            printf "${RED}FAILED${NC}\n"
            echo
        else
            echo 
            printf "${GREEN}SUCCESS${NC}\n"
            echo
        fi
    done

    echo ----- Now lets execute it ! -----
    echo
    echo
    printf "*****${CYAN} BEGIN ${NC}*****\n"
    echo

    java $1

    echo 
    printf "*****${CYAN} END ${NC}*****\n"
    echo

    rm -r *.class
fi
