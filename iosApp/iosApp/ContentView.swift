import SwiftUI
import SharedLogic

struct ContentView: View {
    @State private var showContent = false
    
    @State private var names: [String] = []
    
    var body: some View {
        VStack {
            Text("Hello wrld")
            List(names, id: \.self) { name in
                Text(name)
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
        .task {
            let useCase = ShrineSecretsModule.shared.getCurrentShrineSecrets()
            let result = try? await useCase.invoke()

            guard let result else {
                return
            }
            
            result.fold(
                onSuccess: { data in
                    names = data?.perks.map(\.name) ?? []
                },
                onFailure: { error in
                    print(error)
                }
            )
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
