import pandas as py

path_training = "adult.data-3.csv"
path_test = "adult.test-3.csv"

def readDataFromCSV(filePath):
    allData = py.read_csv(filePath)
    features = list(allData)
    
    descriptiveFeats = features[:-1]
    labelFeats = features[-1]

    descriptives = allData[descriptiveFeats]
    labels = allData[labelFeats]

    return descriptives, labels, allData

def printColumns(_data):
    print("##################")
    print()
    featuresList = list(_data)
    print(featuresList)
    for feature in featuresList:
        print("featureName:", feature)
        uniqueValues = []
        for value in _data[feature]:
            if value not in uniqueValues:
                uniqueValues.append(value)
        print("uniqueValues:", len(uniqueValues))
        print(sorted(uniqueValues))
        print()
    print("##################")

def main():
    trainData, trainLabel, allData = readDataFromCSV(path_training)
    printColumns(trainData)
    
    
main()

# your code
# female/male, cleaned/dirty, age/hours-per-week

featuresToPlot = ["age", "hours-per-week"]
datasetList = [cleanedDataset, allData]
datasetNameList = ["Cleaned", "Original"]
sexList = ["Female", "Male" ]

def generateAxes():
    axes = []
    for dataset in datasetNameList:
        for sex in sexList:
            fig, axs = plt.subplots(1,2)
            for i in range(0,2):
                axs[i].set_title(dataset + " - " + sex)
            axes.append(axs)
    return axes

subplotList = generateAxes()

def applyStyleToPlot(plotObject, edge_color, fill_color):
    #bp = ax.boxplot(data, patch_artist=True)

    for element in ['boxes', 'whiskers', 'fliers', 'means', 'medians', 'caps']:
        plt.setp(plotObject[element], color=edge_color)

    for patch in plotObject['boxes']:
        patch.set(facecolor=fill_color)

def getInformativeTextForLabel(plotObject):
    median = plotObject["medians"][0].get_ydata()[0]        
    mean = plotObject["means"][0].get_ydata()[0]
    textForDownLabel = "Median: " + str(float(median)) + '\n' + "Mean: " + str(float(mean))
    return textForDownLabel

def generatePlot(dataset, sex, features, printIndex):
    datasetCopied = deepcopy(dataset)
    genderFilteredDataset = datasetCopied[datasetCopied["sex"] == sex]
    
    for i in range(len(features)):
        featureName = features[i]
        ax = subplotList[printIndex][i]
        plotObject = genderFilteredDataset.boxplot \
            (column=[featureName], showmeans=True, return_type="dict", ax=subplotList[printIndex][i], patch_artist=True)
        
        textForDownLabel = getInformativeTextForLabel(plotObject)
        ax.set_xlabel(textForDownLabel)
        applyStyleToPlot(plotObject, 'red', 'tan')
        # 'blue', 'cyan'
        
        
        
    

def generateAllPlots():
    index = 0
    for dataset in datasetList:
        for sex in sexList:
            generatePlot(dataset, sex, featuresToPlot, index)
            index += 1
        
generateAllPlots()