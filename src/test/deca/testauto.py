#!/usr/bin/env python3

import subprocess
import glob

print("Ce programme lance")
print("1. La compilation de tous les fichiers .deca situés dans valid ou invalid")
print("2. L'execution des fichiers assembleurs crées")
print("3. La verification de la sortie VS la sortie attendue")

i=0
j=0
k=0

#Suppression des ancien .ass
print("Suppression des anciens fichiers .ass")
files = glob.glob('./src/test/deca/**/valid/*.ass',recursive = True)
for file in files:
    subprocess.run(['rm',file])
    #print("Fichier en cours de suppr valid : " + file)

files = glob.glob('./src/test/deca/**/invalid/*.ass',recursive = True)
for file in files:
    subprocess.run(['rm',file])
    #print("Fichier en cours de suppr invalid : " + file)

#On commence par compiler tous les fichiers en .deca
#Lancement des fichiers situés dans invalid

print("Compilation des fichiers")
files = glob.glob('./src/test/deca/**/invalid/*.deca',
                   recursive = True)

for file in files:
#    i+=1
    #print("Fichier en cours de compilation invalid : " + file)
    cmd = subprocess.run(['decac',file],capture_output=True)
    #print(cmd)
    res1 = cmd.stdout.decode('ascii')
    if (file[:24]=='./src/test/deca/context/'):
        if (res1!=''):
            head, _sep, tail = file.rpartition('.')
            head=head+'.deca'
            filin = open(head, "r")
            lignes = filin.readlines()
            if (len(lignes)>=5):
                res2 = lignes[4][3:]
            else :
                res2="Pas de résultat prévu"
            filin.close()
            if(res1!=res2):
                #k+=1
                print("le fichier "+head+" ne compile pas bien")
                print("Résultat obtenu : ")
                print (res1)
                print("Résultat attendu : ")
                print (res2)

#Lancement des fichiers situés dans valid

files = glob.glob('./src/test/deca/**/valid/*.deca',
                   recursive = True)
for file in files:
#    i+=1
    #print("Fichier en cours de compilation valid : " + file)
    cmd = subprocess.run(['decac',file],capture_output=True)

#On lance ensuite les tests des .ass qui sont dans valid
files = glob.glob('./src/test/deca/**/valid/*.ass',recursive = True)
for file in files:
#    j+=1

    #print("Fichier en cours d'execution : " + file)

    #if listant tous les fichiers spéciaux qui nécssitent des inputs pour être testés
    if file == './src/test/deca/codegen/valid/ReadFloat.ass' :
        #print ("Test avec un Float : ")
        cmd = subprocess.run(['ima',file], input='1.0', text=True, capture_output=True)
        #print (cmd)

    elif file == './src/test/deca/codegen/valid/readExpr.ass' :
        #print ("Test avec un un entier : ")
        subprocess.run(['ima',file], input='1.0', text=True, capture_output=True)

    elif file == './src/test/deca/syntax/valid/moitie.ass' :
        #print ("Test avec un un entier : ")
        subprocess.run(['ima',file], input='1.0', text=True, capture_output=True)

    elif file == './src/test/deca/context/valid/11_ReadInt.ass' :
        #print ("Test avec un un entier : ")
        subprocess.run(['ima',file], input='1.0', text=True, capture_output=True)

    elif file == './src/test/deca/context/valid/26_moitie.ass' :
        #print ("Test avec un un entier : ")
        subprocess.run(['ima',file], input='1.0', text=True, capture_output=True)

    else :
        cmd = subprocess.run(['ima',file], capture_output=True)
        #print (cmd)
        res1 = cmd.stdout.decode('ascii')
        head, _sep, tail = file.rpartition('.')
        head=head+'.deca'
        filin = open(head, "r")
        lignes = filin.readlines()
        if (len(lignes)>=5):
            res2 = lignes[4][3:]
        else :
            res2="Pas de résultat prévu"
        filin.close()
        if(res1!=res2):
#            k+=1
            print("le fichier "+head+" ne donne pas le résultat attendu")
            print("Résultat obtenu : ")
            print (res1)
            print("Résultat attendu : ")
            print (res2)


#On lance ensuite les tests des .ass qui sont dans invalid
files = glob.glob('./src/test/deca/**/invalid/*.ass',
                   recursive = True)
for file in files:
    #j+=1
    #print("Fichier en cours d'execution : " + file)
    cmd = subprocess.run(['ima',file], capture_output=True)
    if (file[:24]!='./src/test/deca/context/'):
        res1 = cmd.stdout.decode('ascii')
        head, _sep, tail = file.rpartition('.')
        head=head+'.deca'
        filin = open(head, "r")
        lignes = filin.readlines()
        if (len(lignes)>=5):
            res2 = lignes[4][3:]
        else :
            res2="Pas de résultat prévu"
        filin.close()
        if(res1!=res2):
    #        k+=1
            print("le fichier "+head+" ne donne pas le résultat attendu")
            print("Résultat obtenu : ")
            print (res1)
            print("Résultat attendu : ")
            print (res2)
#print('----------------BILAN----------------')
#print("Nombre de fichiers .deca : ",i)
#print("Nombre de fichiers ayant compilé correctement : ",j)
#print("Nombre d'erreurs restantes : ",k)

'''
### NOTES MEMO

# FAIRE UNE VERIFICATION DE LA SORTIE D'UN FICHIER ASSEMBLEUR (avec compilation deca)
file = '/home/antoine/Projet_GL/src/test/deca/codegen/invalid/DivZeroFlot.ass'
subprocess.run(['decac',file])
cmd = subprocess.run(['ima',file], capture_output=True)
print(cmd)
print('La sortie de l execution qui nous intéresse = ')
res1 = cmd.stdout.decode('ascii')[:20]
print (res1)

print('à comparer avec la ligne 5 du fichier')
filin = open("/home/antoine/Projet_GL/src/test/deca/codegen/invalid/DivZeroFlot.deca", "r")
lignes = filin.readlines()
res2 = lignes[4][3:23]
print (res2)
filin.close()

if (res1==res2):
    print('Résultat attendu OK')
else :
    print('Résultat attendu non OK')

'''
#### ESSAIS UNITAIRES

#subprocess.call(['ls' -l']);
#args = ['ima', 'src/test/deca/codegen/valid/ReadFloat.ass', '<','src/test/deca/codegen/valid/test.deca']
#args = ['cd','src/test/deca/codegen/valid']
#print(args)
#subprocess.call("(ima ~Projet_GL/src/test/deca/codegen/valid/ReadFloat.ass < ~Projet_GL/src/test/deca/codegen/valid/test.deca)", shell=True)

#args = ['ima', '~Projet_GL/src/test/deca/codegen/valid/ReadFloat.ass' '<' '~Projet_GL/src/test/deca/codegen/valid/test.deca']

#print(args)
#p = subprocess.Popen(args)

#subprocess.Popen("ls", cwd="src/test/deca/codegen/valid")
#subprocess.Popen("ima", cwd="src/test/deca/codegen/valid/PrintVal.ass")

#cmd = subprocess.Popen(['ima','src/test/deca/codegen/valid/ReadFloat.ass','<','src/test/deca/codegen/valid/test.deca'], stdin=subprocess.PIPE)

#subprocess.run(["ls", "-l"]);

#Faire un algorithme Python qui fait la liste des fichiers :
        #from os import walk
        #listeFichiers = []
        #for (repertoire, sousRepertoires, fichiers) in walk(monRepertoire):
            #listeFichiers.extend(fichiers)
#Il récupère les mots en .deca dans une liste et les execute au fur et à mesure :
# 1. decac fichier.deca
# 2. ima fichier.deca : que faire dans les cas où cela nécessite une entrée ?
#Ensuite récupérer la sortie
#Comparer la sortie avec la ligne 5 des fichiers et valider ou non le test avec un print
