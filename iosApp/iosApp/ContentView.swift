import SwiftUI
import shared

struct ContentView: View {
    private struct ComposeView: UIViewControllerRepresentable {
        func makeUIViewController(context: Context) -> UIViewController {
            return ApplicationControllerKt.ApplicationController()
        }
        
        func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
    }
    
    var body: some View {
        ComposeView()
            .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
            .edgesIgnoringSafeArea(.all)
            .ignoresSafeArea(.keyboard)
    }
}
