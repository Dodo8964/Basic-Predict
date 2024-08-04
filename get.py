import sys
from sklearn.tree import DecisionTreeRegressor
from sklearn.pipeline import Pipeline
from sklearn.impute import SimpleImputer
import pandas as pd

def main():
    try:
        
        Nse_stock_path = "D:/Harshith/Mini/NIFTY 500.csv"
        Nse_stock = pd.read_csv(Nse_stock_path)
        Nse_stock = Nse_stock.dropna(axis=0)
        y = Nse_stock.Close
        Nse_Train_Test = ['Open', 'High', 'Low', 'P/E', 'P/B']
        X = Nse_stock[Nse_Train_Test]
        pipeline = Pipeline(steps=[
            ('imputer', SimpleImputer()),
            ('Nse_Model', DecisionTreeRegressor(random_state=1))
        ])
        X = X[:y.shape[0]]
        pipeline.fit(X, y)

        # Read input integer values from Java
        open_val = int(sys.stdin.readline().strip())
        high_val = int(sys.stdin.readline().strip())
        low_val = int(sys.stdin.readline().strip())
        pe_val = int(sys.stdin.readline().strip())
        pb_val = int(sys.stdin.readline().strip())

        # Create DataFrame from input values
        val_df = pd.DataFrame({
            'Open': [open_val],
            'High': [high_val],
            'Low': [low_val],
            'P/E': [pe_val],
            'P/B': [pb_val]
        })

        # Predict using the pre-trained model
        pred = pipeline.predict(val_df)

        # Send result back to Java
        print(int(pred[0]))
        sys.stdout.flush()  # Send data
    except Exception as e:
        print("Error:", e, file=sys.stderr)
        sys.stdout.flush()

if __name__ == "__main__":
    main()
