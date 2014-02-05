'''
Created on Oct 25, 2013

@author: Chunqiu Zeng
'''
import numpy as np
import scipy
import matplotlib.pyplot as plt

class Simulation(object):
    '''
    This class is used to simulate the sequence 
    of events in specific random order
    '''


    def __init__(self):
        '''
        Constructor
        '''
        
    def possion(self):
        return np.random.poisson(lam=5, size=10000);
    
    def exponential(self):
        return np.random.exponential(5, size=50)
    
    def generateEventSeq(self, beta, num):
        data = []
        d1 = []
        d2 = []
        temp = np.random.exponential(beta, num)
        sumT = 0
        for index in range(len(temp)):
            temp[index] = temp[index] + sumT
            d1.append(temp[index])
            sumT = temp[index]
            
        temp = np.random.exponential(beta, num)
        sumT = 0
        for index in range(len(temp)):
            temp[index] = temp[index] + sumT
            d2.append(temp[index])
            sumT = temp[index]
            
        l1 = d1[-1]
        l2 = d2[-1]
        if(l1 < l2):
            data.append(d1)
            data.append([x for x in d2 if x <= l1])
        else:
            data.append([x for x in d1 if x < l2])
            data.append(d2)
            
        return data
            
    def generateDependenceSeq(self, beta, num):
        data = []
        d1 = []
        d2 = []
        d3 = []
#         generate d1
        temp = np.random.exponential(beta, num)
        sumT = 0
        for index in range(len(temp)):
            temp[index] = temp[index] + sumT
            d1.append(temp[index])
            sumT = temp[index]
#          generate d3
        temp = np.random.exponential(beta, num)
        sumT = 0
        for index in range(len(temp)):
            temp[index] = temp[index] + sumT
            d3.append(temp[index])
            sumT = temp[index]   
#         generate d2
        lag = beta    
        for d in d1:
#             d2.append(d+lag)
            end = d + lag + (np.random.uniform() - 0.5) * 0.1 * beta
            if self.findD3(d3, d, end):
                d2.append(end)
            
        data.append(d1)
        data.append(d2)
        data.append(d3)
            
        return data
    
    def findD3(self, d3, start, end):
        for d in d3:
            if d < end and d > start:
                return True
        return False
    
    def estimateDistr(self, data):
        temp = []
        x = 0
        for t in data:
            temp.append(t - x)
            x = t
        temp.sort()
        lenB = len(data)
        lastT = data[-1]
        ret = []
        sumT = 0
        for i in range(len(temp)):
            ret.append((sumT + (lenB - i) * temp[i]) / (lastT * 1.0))
            sumT = sumT + temp[i]
        
        retdata = []
        retdata.append(temp)
        retdata.append(ret)
        return retdata
    
    def estimateConditionalDistr(self, data):
        
        temp = []
        for a in data[0]:
            for b in data[1]:
                if a < b:
                    temp.append(b - a)
                    break
        temp.sort()
        ret = []
        lenA = len(temp)
        for index in range(len(temp)):
            ret.append(index * 1.0 / lenA)
        
        retdata = []
        retdata.append(temp)
        retdata.append(ret)
        return retdata
    
    def diffDistr(self, data1, data2):
        t1 = data1[0]
        r1 = data1[1]
        t2 = data2[0]
        r2 = data2[1]
        len1 = len(t1)
        len2 = len(t2)
        i = 0
        j = 0
        prevT1 = 0
        prevR1 = 0
        prevT2 = 0
        prevR2 = 0
        t = [0]
        r = [0]
        while i < len1 and j < len2:
#           avoid the same time point  
            if(i < len1 - 1 and t1[i] == t1[i + 1]):
                i = i + 1
                continue
            if(j < len2 - 1 and t2[j] == t2[j + 1]):
                j = j + 1
                continue
#           merge two sequence data  
            if(t1[i] == t2[j]):
                t.append(t1[i])
                r.append(r1[i] - r2[j])
                prevT1 = t1[i]
                prevR1 = r1[i]
                prevT2 = t2[j]
                prevR2 = r2[j]
                i = i + 1
                j = j + 1
            elif(t1[i] < t2[j]):
                t.append(t1[i])
                tempR = (t1[i] - prevT2) / (t2[j] - prevT2) * (r2[j] - prevR2) + prevR2
                r.append(r1[i] - tempR)
                prevT1 = t1[i]
                prevR1 = r1[i]
                i = i + 1
            else:
                t.append(t2[j])
                tempR = (t2[j] - prevT1) / (t1[i] - prevT1) * (r1[i] - prevR1) + prevR1
                r.append(tempR - r2[j])
                prevT2 = t2[j]
                prevR2 = r2[j]
                j = j + 1
                
        while i < len1:
            t.append(t1[i])
            r.append(r1[i] - 1.0)
            i = i + 1
            
        while j < len2:
            t.append(t2[j])
            r.append(1.0 - r2[j])
            j = j + 1
        ret = []
        for i in range(len(t)):
            temp = (t[i], r[i])
            ret.append(temp)   
        return ret
    
    def derivative(self,data):
        dataLen = len(data)
        ret=[]
        i=1
        prevT = data[0][0]
        prevR = data[0][1]
        for i in  range(dataLen):
            if i == 0:
                continue
            Rdelta = data[i][1] - prevR
            Tdelta = data[i][0] - prevT
            if Tdelta == 0:
                continue
            ret.append((data[i][0],Rdelta/Tdelta))
            prevR=data[i][1]
            prevT=data[i][0]
        return ret
        
    def sortDescending(self,data):
        ret=sorted(data,key=lambda d:-1*d[-1])
        return ret
    
    def findConfidence(self,interval,delta,pData):
        l = len(pData[0])
        data=[]
        for i in range(l):
            data.append((pData[0][i],pData[1][i]))
        minV = interval - delta
        maxV = interval + delta
        max_tuple = 0.0
        min_tuple = 0.0
        lenData = len(data)
        for i in range(lenData):
            if minV> data[i][0]:
                min_tuple=data[i][1]
            else:
                break
        for i in reversed(range(lenData)):
            max_tuple=data[i][1]
            if maxV< data[i][0]:
                continue
            else:
                break
        return max_tuple - min_tuple
        
