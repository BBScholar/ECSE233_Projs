import math
import csv

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import statsmodels.api as sm
from statsmodels.formula.api import ols

def plot(fn: str):

    df = pd.read_csv(fn)

    fig = plt.figure(figsize=(12,8))

    model = ols('r ~ n', data=df).fit()

    fig = sm.graphics.plot_regress_exog(model, 'n', fig=fig)
    fig.savefig("plots")

    with open("stats.txt", "w") as file:
        file.write(str(model.summary()))


if __name__ == "__main__":
    plot("runtimes.csv")
