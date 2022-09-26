import glob
import cv2
import numpy as np
import tensorflow as tf
import time

# citim fisierul train.txt si apoi il impartim in 2 vectori, unul cu id-urile pozelor si unul cu label-ul pozelor
train_labels_txt = np.loadtxt("./data/train.txt", dtype=list, skiprows=1)
train_labels = []
train_ids = []
for i in train_labels_txt:
    x = i.split(',')
    train_labels.append(x)
    train_ids.append(i.split(',')[0])
# sortam aceste array-uri
train_labels.sort()
train_ids.sort()

# citim fisierul validation.txt si apoi il impartim in 2 vectori, unul cu id-urile pozelor si unul cu label-ul pozelor
validation_labels_txt = np.loadtxt("./data/validation.txt", dtype=list, skiprows=1)
validation_labels = []
validation_ids = []
for i in validation_labels_txt:
    validation_labels.append(i.split(','))
    validation_ids.append(i.split(',')[0])
validation_labels.sort()
validation_ids.sort()

# adaugam label-urile intr-un array pentru train si le mai adaugam si intr-un array unde vor fi adaugate si cele de
# validare, astfel incat atunci cand se da predict la imaginile de test, sa aiba un pool cat mai mare de invatare
train_data_labels = []
t = []
for i in range(len(train_labels)):
    t.append(float(train_labels[i][1]))
    del train_labels[i][0]
train_data_labels += t
train_labels = np.array(t)
t = []
for i in range(len(validation_labels)):
    t.append(float(validation_labels[i][1]))
    del validation_labels[i][0]
train_data_labels += t
validation_labels = np.array(t)

# adaugam numele pozelor de test si le sortam
test_filenames = []
for fn in glob.glob('./data/test/*'):
    test_filenames.append(fn)

test_filenames.sort()

# adaugam numele pozelor de train si validare in functie de in ce .txt se afla numele pozei si le sortam

train_filenames = []
validation_filenames = []
for fn in glob.glob('./data/train+validation/*'):
    if fn[24:] in validation_ids:
        validation_filenames.append(fn)
    else:
        train_filenames.append(fn)

validation_filenames.sort()
train_filenames.sort()

# citim imaginile dupa numele din array-ul cu nume, le inversam culorile, ele fiind citite in format BGR si le
# impartim valorile pixelilor la 255, pentru a fi intre 0 si 1, lucru care ajuta la acuratete pentru unele modele
train_images = []
for fn in train_filenames:
    im = cv2.imread(fn) / 255.0
    im = im[:, :, ::-1]
    train_images.append(im)

validation_images = []
for fn in validation_filenames:
    im = cv2.imread(fn) / 255.0
    im = im[:, :, ::-1]
    validation_images.append(im)
train_data = train_images + validation_images

test_images = []
for fn in test_filenames:
    im = cv2.imread(fn) / 255.0
    im = im[:, :, ::-1]
    test_images.append(im)
test_images = np.array(test_images)

train_images = np.array(train_images)

from sklearn.neural_network import MLPClassifier

train_data_labels = np.array(train_data_labels)
train_data = np.array(train_data)

# dam flatten la imagini, ele fiind reprezentate de un vector pe 3 sau 4 dimensiuni, in functie de cum preluam
# imaginea, acestea devenind un vector unidimensional
nr, nx, ny, nsamples = train_images.shape
d2_train_dataset = train_images.reshape((nr, nx * ny * nsamples))

nr2, nx2, ny2, nsamples2 = train_images.shape
d2_validation_dataset = train_images.reshape((nr2, nx2 * ny2 * nsamples2))

# definim modelul
model = MultinomialNB()

# dam fit la datele de train cu label-urile specifice
model.fit(d2_train_dataset, train_labels)

# dam predict in functie de ce a fost antrenat
test_predictions = model.predict(d2_validation_dataset)

# deschidem fisierul si scriem in acesta id-ul imaginii, urmat de label-ul acesteia
f = open('out1.txt', 'w')
f.write("id,label\n")
for i in range(len(test_filenames)):
    f.write(f"{test_filenames[i][12:]},{int(test_predictions[i])}\n")
