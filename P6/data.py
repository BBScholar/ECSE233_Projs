import pandas as pd
import numpy as np
from scipy.interpolate import interp1d

import matplotlib.pyplot as plt


df = pd.read_csv("data.csv")
df['array'] = df['a'];
df['heap'] = df['h'];
del df['a']
del df['h']

df.plot(x="n")
plt.show()
