import subprocess
import glob

#Lancement des fichiers situés dans valid

#On commence par compiler tous les fichiers en .deca

files = glob.glob('/home/antoine/Projet_GL/src/test/deca/**/valid/*.deca',
                   recursive = True)
for file in files:
    print("Fichier en cours de compilation : " + file)
    subprocess.run(['decac',file])

#On lance ensuite les tests des .ass
files = glob.glob('/home/antoine/Projet_GL/src/test/deca/**/valid/*.ass',
                   recursive = True)
for file in files:
    print("Fichier en cours d'execution : " + file)
    if file == '/home/antoine/Projet_GL/src/test/deca/codegen/valid/ReadFloat.ass' :
        print ("Test avec un Float : ")
        cmd = subprocess.run(['ima',file], input='1.0', text=True)
        print (cmd)
    if file == '/home/antoine/Projet_GL/src/test/deca/codegen/valid/while.ass' :
        print ("Test avec un numéro 0 pour ne pas entrer dans le while : ")
        cmd = subprocess.run(['ima',file], input='0.0', text=True)
        print (cmd)

    else :
        cmd = subprocess.run(['ima',file])
        print (cmd)



#Lancement des fichiers situés dans invalid

#On commence par compiler tous les fichiers en .deca
files = glob.glob('/home/antoine/Projet_GL/src/test/deca/**/invalid/*.deca',
                   recursive = True)
for file in files:
    print("Fichier en cours de compilation : " + file)
    subprocess.run(['decac',file])


#On lance ensuite les tests des .ass
files = glob.glob('/home/antoine/Projet_GL/src/test/deca/**/invalid/*.ass',
                   recursive = True)
for file in files:
    print("Fichier en cours d'execution : " + file)
    if file == '/home/antoine/Projet_GL/src/test/deca/codegen/valid/ReadFloat.ass' :
        print ("Test avec un Float : ")
        subprocess.run(['ima',file], input='1.0', text=True)

    else :
        subprocess.run(['ima',file])









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
