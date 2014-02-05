'''
Created on Jan 27, 2014

@author: grand
'''
import numpy as np
import scipy
import matplotlib.pyplot as plt
import math
import sys

class Simulator(object):
    '''
    Generate synthetic data
    '''


    def __init__(self):
        '''
        Constructor
        '''
    
    def generateEvent(self, beta, num):
        data = []
        sumT = 0
        temp = np.random.exponential(beta, num)
        for index in range(len(temp)):
            temp[index] = temp[index] + sumT
            data.append(temp[index])
            sumT = temp[index]
        return data
    
    def generateDependence(self, beta, num, miu, sigmaSqrt):
        data = []
        
        d1 = self.generateEvent(beta, num)
        
        d2 = []
    
        for d in d1:
            t = d + np.random.normal(miu, math.sqrt(sigmaSqrt)) 
            d2.append(t)
        data.append(d1)
        data.append(d2)
        return data
    
    def generateEXPDependence(self,beta,num,lbd):
        data = []
        
        d1 = self.generateEvent(beta, num)
        
        d2 = []
    
        for d in d1:
            t = d + np.random.exponential(1/lbd) 
            d2.append(t)
        data.append(d1)
        data.append(d2)
        return data
            
    def where(self, miu, sigmaSqrt):
        pass
    
    def printData(self, data, file):
        num = 0
        for d in data:
            num = len(d)
        print >> file, num
        
        
        for i in range(len(data)):
            for e in data[i]:
                print >> file, "%d,%f" % (i, e)
    
    def outputFile(self, data, fileName):
        file = open(fileName, 'w')
        self.printData(data, file)
        file.close()
        
        
if __name__=='__main__':
    beta=0.1
    num =100
    lbd=5.0 
    fileName ="syntetic_%f_%d_%f.data" %(beta,num,lbd)
    sim = Simulator()
    data=sim.generateEXPDependence(beta, num, lbd)
    sim.printData(data,sys.stdout)
    sim.outputFile(data, fileName)   
    
if __name__ == '__main__1':
    beta = 0.1
    num = 100
    miu = 10
    sigmaSqrt = 4
    fileName = "sythetic_%f_%d_%f_%f.data" % (beta, num, miu, sigmaSqrt)
    sim = Simulator();
    
    data = sim.generateDependence(beta, num, miu, sigmaSqrt)
    sim.printData(data, sys.stdout)
    
    sim.outputFile(data, fileName)
